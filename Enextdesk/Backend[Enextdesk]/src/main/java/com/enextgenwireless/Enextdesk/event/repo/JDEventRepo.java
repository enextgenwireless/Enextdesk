package com.enextgenwireless.Enextdesk.event.repo;

import com.enextgenwireless.Enextdesk.event.domain.EnextgenEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDEventRepo extends JpaRepository<EnextgenEvent, Long> {

}