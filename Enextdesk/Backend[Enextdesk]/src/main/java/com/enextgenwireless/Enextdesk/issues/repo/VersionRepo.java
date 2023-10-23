package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Version;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VersionRepo extends JpaRepository<Version, Long> {

    Set<Version> findByProject(Project project);

    Version findByProjectAndId(Project project, Long id);

}
