package com.enextgenwireless.Enextdesk.git.repo;

import com.enextgenwireless.Enextdesk.git.domain.Repository;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RepositoryRepo extends JpaRepository<Repository, Long> {

    Set<Repository> findAllByProject(Project project);

    Repository findByUuid(String uuid);

}