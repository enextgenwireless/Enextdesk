package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Workflow;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepo extends JpaRepository<Workflow, Long> {

    Workflow findByName(String name);

    List<Workflow> findByProject(Project project);

}
