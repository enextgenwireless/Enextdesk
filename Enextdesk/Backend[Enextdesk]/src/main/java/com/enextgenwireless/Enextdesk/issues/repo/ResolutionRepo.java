package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolutionRepo extends JpaRepository<Resolution, Long> {
    Resolution findByName(String name);
}
