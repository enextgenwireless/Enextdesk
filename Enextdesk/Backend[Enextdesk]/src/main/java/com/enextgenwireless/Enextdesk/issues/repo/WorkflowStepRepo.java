package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Workflow;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowStep;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WorkflowStepRepo extends JpaRepository<WorkflowStep, Long> {

    Set<WorkflowStep> findByWorkflow(Workflow workflow);

    Set<WorkflowStep> findByWorkflowProject(Project project);

}
