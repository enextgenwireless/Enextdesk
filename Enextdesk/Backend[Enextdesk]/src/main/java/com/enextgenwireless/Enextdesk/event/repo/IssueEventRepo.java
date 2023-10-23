package com.enextgenwireless.Enextdesk.event.repo;

import com.enextgenwireless.Enextdesk.event.domain.IssueEvent;
import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface IssueEventRepo extends JpaRepository<IssueEvent, Long> {
    @Transactional
    @Modifying
    void deleteByIssue(Issue issue);
}