package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProjectRepo extends JpaRepository<Project, Long> {

    Set<Project> findAllByOrderByNameAsc();

    Project findByName(String name);

    Project findByKey(String key);
}
