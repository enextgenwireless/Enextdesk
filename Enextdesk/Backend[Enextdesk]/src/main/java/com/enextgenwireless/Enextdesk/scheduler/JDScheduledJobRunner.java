package com.enextgenwireless.Enextdesk.scheduler;

import com.enextgenwireless.Enextdesk.event.domain.IssueEvent;
import com.enextgenwireless.Enextdesk.event.domain.IssueEventHandler;
import com.enextgenwireless.Enextdesk.event.domain.IssueEventType;
import com.enextgenwireless.Enextdesk.issues.service.IssueService;
import com.enextgenwireless.Enextdesk.project.service.ProjectService;
import lombok.extern.java.Log;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;

@Log
public class JDScheduledJobRunner extends QuartzJobBean {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueEventHandler issueEventService;

    @Autowired
    private ProjectService projectService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("Executing Job with key " + jobExecutionContext.getJobDetail().getKey());

        //JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        if ("duedatenotifier".equals(jobExecutionContext.getJobDetail().getKey().getName())) {
            issueService.findByDueDateBeforeAndEqual(LocalDate.now()).stream().filter(i -> i.getResolution() == null).forEach(i -> {
                log.info("Sending due for " + i.getSummary());
                issueEventService.sendMessage(new IssueEvent(IssueEventType.DUE, i, null, null, null, null));
            });
        }
    }
}
