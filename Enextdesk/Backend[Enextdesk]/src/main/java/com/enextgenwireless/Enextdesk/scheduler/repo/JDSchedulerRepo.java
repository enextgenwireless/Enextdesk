package com.enextgenwireless.Enextdesk.scheduler.repo;

import com.enextgenwireless.Enextdesk.scheduler.domain.JDScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDSchedulerRepo extends JpaRepository<JDScheduler, Long> {

    JDScheduler findByJobID(String id);

}