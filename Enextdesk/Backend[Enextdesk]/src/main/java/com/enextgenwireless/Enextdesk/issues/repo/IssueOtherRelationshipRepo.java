package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.domain.IssueOtherRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IssueOtherRelationshipRepo extends JpaRepository<IssueOtherRelationship, Long> {
    Set<IssueOtherRelationship> findByIssue(Issue issue);
}