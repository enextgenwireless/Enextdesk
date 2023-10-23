package com.enextgenwireless.Enextdesk.issues.service;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.event.domain.*;
import com.enextgenwireless.Enextdesk.event.domain.*;
import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.domain.IssueHistory;
import com.enextgenwireless.Enextdesk.issues.repo.IssueSearchCustomRepo;
import com.enextgenwireless.Enextdesk.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log
public class IssueAsyncService {


    @Autowired
    private IssueEventHandler issueEventService;
    @Autowired
    private EnextgenEventHandler jdEventService;
    @Autowired
    private IssueSearchCustomRepo issueSearchCustomRepo;
    @Autowired
    private IssueEventHandler issueEventHandler;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    @Async
    public void logHistory(IssueEventType type, Issue issue, String field, String summary, String oldValue, String newValue, Login user) {
        if ((oldValue == null && newValue != null) || ((oldValue != null) && !oldValue.equals(newValue))) {
            IssueHistory h = new IssueHistory();
            h.setBy(user);
            h.setLoggedOn(LocalDate.now());
            h.setIssue(issue);
            h.setField(field);
            h.setSummary(summary);
            h.setOldValue(oldValue);
            h.setNewValue(newValue);
            issueEventService.LogHistory(h);
            issueEventService.sendMessage(new IssueEvent(type, issue, field, oldValue, newValue, user));
            switch (type) {
                case CREATE:
                    triggerEvent(EnextgenEventType.ISSUE_CREATE, issue, field, oldValue, newValue);
                    break;
                case UPDATE:
                    triggerEvent(EnextgenEventType.ISSUE_UPDATE, issue, field, oldValue, newValue);
                    break;
                case ASSIGN:
                case REASSIGN:
                    triggerEvent(EnextgenEventType.ISSUE_ASSIGN, issue, field, oldValue, newValue);
                    break;
                case ATTACH:
                    triggerEvent(EnextgenEventType.ISSUE_ATTACHMENT_CREATE, issue, field, oldValue, newValue);
                    break;
                case ATTACH_DELETE:
                    triggerEvent(EnextgenEventType.ISSUE_ATTACHMENT_DELETE, issue, field, oldValue, newValue);
                    break;
                case COMMENT:
                    if (ObjectUtils.isEmpty(oldValue))
                        triggerEvent(EnextgenEventType.ISSUE_COMMENT_CREATE, issue, field, oldValue, newValue);
                    else
                        triggerEvent(EnextgenEventType.ISSUE_COMMENT_UPDATE, issue, field, oldValue, newValue);
                    break;
                case COMMENT_DELETE:
                    triggerEvent(EnextgenEventType.ISSUE_COMMENT_DELETE, issue, field, oldValue, newValue);
                    break;
                case DELETE:
                    triggerEvent(EnextgenEventType.ISSUE_DELETE, issue, field, oldValue, newValue);
                    break;
            }
        }

    }

    @Async
    public void trackMentions(Issue issue, String oldText, String newText, String field, Login user) {
        Set<Login> members = projectService.getMembersByProjectKey(issue.getProject().getKey());
        //Matcher m = Pattern.compile(helperUtil.getUserMentionMatcherRegex()).matcher(newText);
        Matcher m = Pattern.compile("@[A-Za-z0-9.+_-]*").matcher(newText);
        while (m.find()) {
            String match = m.group();
            members.stream().filter(l -> l.getUserName().equalsIgnoreCase(match.substring(1))).forEach(l -> {
                addWatcher(issue, l);
                issueEventService.sendMessage(new IssueEvent(IssueEventType.MENTION, issue, field, oldText, newText, user, l));
            });
        }
    }

    @Async
    @CacheEvict(value = "watchers", key = "#issue.id")
    public void addWatcher(Issue issue, Login watcher) {
        issueEventService.addWatcher(issue, watcher);
    }

    public void triggerEvent(EnextgenEventType enextgenEventType, Issue issue, String field, String oldValue, String newValue) {
        jdEventService.sendMessage(enextgenEventType, issue, field, oldValue, newValue);
    }

    @Async
    public void updateIssue(Issue issue) {
        issueSearchCustomRepo.update(issueEventHandler.jsonData(issue), issue);
        wsTemplate.convertAndSend("/topic/issue",
                new WSEvent(issue.getProject().getKey(), issue.getKeyPair(), issue.getUpdated().getTime()));
    }

}

@AllArgsConstructor
@Data
class WSEvent {
    private String project;
    private String issue;
    private Long updated;
}
