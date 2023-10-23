package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.project.domain.Project;
import com.enextgenwireless.Enextdesk.project.domain.TimeTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingRepo extends JpaRepository<TimeTracking, Long> {

    TimeTracking findByProject(Project project);


}
