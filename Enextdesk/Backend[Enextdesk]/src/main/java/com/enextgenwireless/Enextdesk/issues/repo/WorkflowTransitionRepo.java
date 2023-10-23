package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Workflow;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WorkflowTransitionRepo extends JpaRepository<WorkflowTransition, Long> {

    WorkflowTransition findByName(String name);

    Set<WorkflowTransition> findByWorkflow(Workflow workflow);

}
