package com.enextgenwireless.Enextdesk.event.domain;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.event.repo.JDEventRepo;
import com.enextgenwireless.Enextdesk.event.repo.SubscriptionRepo;
import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.repo.IssueRepo;
import com.enextgenwireless.Enextdesk.issues.service.IssueService;
import com.enextgenwireless.Enextdesk.issues.service.WorkLogService;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import com.enextgenwireless.Enextdesk.project.domain.TimeTracking;
import com.enextgenwireless.Enextdesk.project.service.ProjectService;
import com.enextgenwireless.Enextdesk.webhook.domain.WebHook;
import com.enextgenwireless.Enextdesk.webhook.domain.WebHookLog;
import com.enextgenwireless.Enextdesk.webhook.domain.WebHookLogHeaders;
import com.enextgenwireless.Enextdesk.webhook.repo.WebHookLogRepo;
import com.enextgenwireless.Enextdesk.webhook.repo.WebHookRepo;
import lombok.extern.java.Log;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class EnextgenEventHandler {

    @Autowired
    private WebHookRepo webHookRepo;
    @Autowired
    private WebHookLogRepo webHookLogRepo;
    @Autowired
    private IssueRepo issueRepo;
    @Autowired
    private IssueService issueService;
    @Autowired
    private WorkLogService workLogService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private JDEventRepo jdEventRepo;

    @Scheduled(initialDelay = 1000, fixedDelay = 300000)
    protected void handleEvent() {
        long c = jdEventRepo.count();
        log.info(" -- Issues: " + c);
        if (c > 0) {
            List<EnextgenEvent> issues = jdEventRepo.findAll();
            issues.forEach(this::handleMessage);
        }
    }

    public void handleMessage(final EnextgenEvent event) {
        try {
            log.info("Received Event :" + event.getEnextgenEventType());
            webHookRepo.findByActiveTrue().stream().filter(wh -> {
                if (wh.isAllProjects()) return true;
                else
                    return wh.getProjects().stream().map(Project::getId).anyMatch(pId -> pId.equals(event.getIssueID()));
            }).filter(wh -> {
                if (wh.isAllEvents()) return true;
                else return wh.getEventTypes().contains(event.getEnextgenEventType());
            }).forEach(webHook -> call(webHook, event));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String projectKey = issueService.getProjectKeyFromPair(event.getIssueKeyPair());
            subscriptionRepo.findByTypeAndProjectKey(event.enextgenEventType, projectKey).forEach(s -> {
                callSubscription(s, event);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdEventRepo.delete(event);
        }
    }

    public void sendMessage(EnextgenEventType enextgenEventType, Issue issue, String field, String oldValue, String newValue) {
        if (null != issue && null != enextgenEventType) {
            jdEventRepo.save(new EnextgenEvent(enextgenEventType, issue.getId(), issue.getProject().getId(), issue.getKeyPair(), field, oldValue, newValue));
        }
    }

    private void call(WebHook webHook, EnextgenEvent event) {
        WebHookLog webHookLog = new WebHookLog();
        webHookLog.setEndPoint(webHook.getEndPoint());
        webHookLog.setEventTypes(event.enextgenEventType);
        webHookLog.setIssueId(event.getIssueID());
        webHookLog.setWebhookId(webHook.getId());
        //Headers
        List<WebHookLogHeaders> headers = new ArrayList<>();
        headers.add(new WebHookLogHeaders(null, "REQUEST", "Accept", "application/json"));
        headers.add(new WebHookLogHeaders(null, "REQUEST", "Content-type", "application/json"));
        headers.add(new WebHookLogHeaders(null, "REQUEST", "user-agent", "JD-WH"));
        webHook.getRequestHeaders().forEach(webHookHeaders -> headers.add(new WebHookLogHeaders(null, "REQUEST", webHookHeaders.getKey(), webHookHeaders.getValue())));
        int statusCode = 0;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(webHook.getEndPoint());
            String requestBody = calculateRequestBody(event);
            webHookLog.setRequestBody(requestBody);
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            headers.forEach(webHookLogHeaders -> httpPost.setHeader(webHookLogHeaders.getKey(), webHookLogHeaders.getValue()));

            CloseableHttpResponse response = client.execute(httpPost);
            for (Header header : response.getAllHeaders()) {
                headers.add(new WebHookLogHeaders(null, "RESPONSE", header.getName(), header.getValue()));
            }
            webHookLog.setResponseBody(EntityUtils.toString(response.getEntity()));
            statusCode = response.getStatusLine().getStatusCode();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            webHookLog.setResponseBody(e.getMessage());
        } finally {
            webHookLog.setHeaders(HelperUtil.squiggly("base", headers));
            webHookLog.setStatusCode(statusCode);
            webHookLog = webHookLogRepo.save(webHookLog);
            log.info("WH call complete: " + webHookLog.getId() + ", status:" + webHookLog.getStatusCode());
        }
    }

    private void callSubscription(Subscription subscription, EnextgenEvent event) {
        WebHookLog webHookLog = new WebHookLog();
        webHookLog.setEndPoint(subscription.getHookurl());
        webHookLog.setEventTypes(event.enextgenEventType);
        webHookLog.setIssueId(event.getIssueID());
        webHookLog.setWebhookId(subscription.getId());
        //Headers
        List<WebHookLogHeaders> headers = new ArrayList<>();
        headers.add(new WebHookLogHeaders(null, "REQUEST", "Accept", "application/json"));
        headers.add(new WebHookLogHeaders(null, "REQUEST", "Content-type", "application/json"));
        headers.add(new WebHookLogHeaders(null, "REQUEST", "user-agent", "JD-WH"));
        //webHook.getRequestHeaders().forEach(webHookHeaders -> headers.add(new WebHookLogHeaders(null, "REQUEST", webHookHeaders.getKey(), webHookHeaders.getValue())));
        int statusCode = 0;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(subscription.getHookurl());
            String requestBody = calculateRequestBody(event);
            webHookLog.setRequestBody(requestBody);
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            headers.forEach(webHookLogHeaders -> httpPost.setHeader(webHookLogHeaders.getKey(), webHookLogHeaders.getValue()));

            CloseableHttpResponse response = client.execute(httpPost);
            for (Header header : response.getAllHeaders()) {
                headers.add(new WebHookLogHeaders(null, "RESPONSE", header.getName(), header.getValue()));
            }
            webHookLog.setResponseBody(EntityUtils.toString(response.getEntity()));
            statusCode = response.getStatusLine().getStatusCode();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            webHookLog.setResponseBody(e.getMessage());
        } finally {
            webHookLog.setHeaders(HelperUtil.squiggly("base", headers));
            webHookLog.setStatusCode(statusCode);
            webHookLog = webHookLogRepo.save(webHookLog);
            log.info("Subscription call complete: " + webHookLog.getId() + ", status:" + webHookLog.getStatusCode());
        }
    }

    private String calculateRequestBody(EnextgenEvent event) {
        String filter = "base,-renderedDescription,-votes,project[-wiki,-lead]," +
                "reporter[-pic,-locked],issueType[-iconUrl],currentStep[issueStatus[-by,-editable,-deletable]]" +
                ",assignee[-pic,-locked],-updateField,customFields";
        switch (event.getEnextgenEventType()) {
            case ISSUE_CREATE:
            case ISSUE_UPDATE:
            case ISSUE_ASSIGN:
            case ISSUE_COMMENT_CREATE:
            case ISSUE_COMMENT_UPDATE:
            case ISSUE_COMMENT_DELETE:
            case ISSUE_ATTACHMENT_CREATE:
            case ISSUE_ATTACHMENT_DELETE:
                return getIssueObject(event, filter).toString();
            case ISSUE_DELETE:
                return addEventData(event, new JSONObject(HelperUtil.squiggly(filter, new JSONObject().put("key", event.getIssueKeyPair())))).toString();
            default:
                throw new IllegalArgumentException();
        }
    }

    private JSONObject getIssueObject(EnextgenEvent event, String filter) {
        Issue i = issueRepo.findById(event.getIssueID()).get();
        Project project = projectService.getProject(i.getProject().getId());
        i.setCustomFields(issueService.getCustomFields(project, i));
        TimeTracking tts = projectService.getTimeTrackingSettings(project);
        i.setEstimateString(workLogService.minutesToString(i.getTimeOriginalEstimate(), tts));
        i.setTimeSpent(workLogService.getWorkLoggedForIssue(i));
        i.setTimeSpentString(workLogService.minutesToString(i.getTimeSpent(), tts));
        return addEventData(event, new JSONObject(HelperUtil.squiggly(filter, i)));
    }

    private JSONObject addEventData(EnextgenEvent event, JSONObject object) {
        JSONObject eventObject = new JSONObject();
        eventObject.put("event", event.getEnextgenEventType())
                .put("change", event.getField())
                .put("old", event.getOldValue())
                .put("new", event.getNewValue());
        return object.put("event", eventObject);
    }
}
