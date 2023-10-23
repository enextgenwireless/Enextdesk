package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.domain.IssueMentions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IssueMentionsRepo extends JpaRepository<IssueMentions, Long> {

    Set<IssueMentions> findByLinkTrue();

    Set<IssueMentions> findByMentionTrue();

    Set<IssueMentions> findByIssue(Issue issue);

}