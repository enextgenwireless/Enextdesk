package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.project.domain.EnextgenConfiguration;
import com.enextgenwireless.Enextdesk.project.service.ConfigurationService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDConfigurationRepo extends JpaRepository<EnextgenConfiguration, Long> {
    EnextgenConfiguration findByKey(ConfigurationService.JDCONFIG key);
}
