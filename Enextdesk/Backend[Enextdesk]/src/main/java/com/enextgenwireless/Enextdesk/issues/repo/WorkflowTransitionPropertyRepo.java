package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransition;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransitionProperties;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransitionPropertySubTypes;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransitionPropertyTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WorkflowTransitionPropertyRepo extends JpaRepository<WorkflowTransitionProperties, Long> {

    Set<WorkflowTransitionProperties> findAllByTransition(WorkflowTransition transition);

    Set<WorkflowTransitionProperties> findAllByTransitionAndType(WorkflowTransition transition, WorkflowTransitionPropertyTypes workflowTransitionPropertyTypes);

    Set<WorkflowTransitionProperties> findAllByTransitionAndTypeAndSubType(WorkflowTransition transition,
                                                                           WorkflowTransitionPropertyTypes workflowTransitionPropertyTypes,
                                                                           WorkflowTransitionPropertySubTypes workflowTransitionPropertySubTypes);

    Set<WorkflowTransitionProperties> findAllByTransitionAndSubType(WorkflowTransition transition,
                                                                    WorkflowTransitionPropertySubTypes workflowTransitionPropertySubTypes);

}
