package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Comment;
import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Set<Comment> findByIssueOrderByCreatedDateDesc(Issue issue);
}