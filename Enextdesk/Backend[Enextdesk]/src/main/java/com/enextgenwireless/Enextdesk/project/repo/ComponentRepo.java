package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.project.domain.Component;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ComponentRepo extends JpaRepository<Component, Long> {

    Set<Component> findByName(String name);

    Set<Component> findByProject(Project project);

    Component findByProjectAndId(Project project, Long fieldId);

    @Query(value = "select * from component where project IN (?1)", nativeQuery = true)
    Set<Component> findAllByProject(Set<Long> projects);
}
