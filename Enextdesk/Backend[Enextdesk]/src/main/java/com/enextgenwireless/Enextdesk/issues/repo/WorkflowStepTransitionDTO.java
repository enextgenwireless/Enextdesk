package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.WorkflowStep;
import com.enextgenwireless.Enextdesk.issues.domain.WorkflowTransition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkflowStepTransitionDTO {

    private WorkflowStep step;

    private List<WorkflowTransition> workflowTransitions;

}
