package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.project.domain.GlobalGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GlobalGroupRepo extends JpaRepository<GlobalGroup, Long> {

    Set<GlobalGroup> findByNameOrderByNameAsc(String name);

    Set<GlobalGroup> findByUsersOrderByNameAsc(Login l);

}
