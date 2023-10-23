package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.project.domain.Group;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepo extends JpaRepository<Group, Long> {

    Set<Group> findByNameOrderByNameAsc(String name);

    Group findByProjectAndId(Project project, Long id);

    Set<Group> findByProjectOrderByNameAsc(Project project);

    Set<Group> findByUsersOrderByNameAsc(Login l);

    Set<Group> findByAllUsersTrueOrderByNameAsc();

}
