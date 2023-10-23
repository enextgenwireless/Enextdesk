package com.enextgenwireless.Enextdesk.git.repo;

import com.enextgenwireless.Enextdesk.git.domain.GitBranch;
import com.enextgenwireless.Enextdesk.git.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GitBranchRepo extends JpaRepository<GitBranch, Long> {
    Set<GitBranch> findByIssuesContainingIgnoreCase(String issueKey);

    Long countByIssuesContainingIgnoreCase(String issueKey);

    GitBranch findByNameAndRepository(String name, Repository repository);

    GitBranch findByName(String name);

}