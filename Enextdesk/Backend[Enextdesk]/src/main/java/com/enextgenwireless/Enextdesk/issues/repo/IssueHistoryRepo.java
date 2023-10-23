package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.domain.IssueHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IssueHistoryRepo extends JpaRepository<IssueHistory, Long> {

    Set<IssueHistory> findByIssueOrderByUpdatedDesc(Issue issue);
}