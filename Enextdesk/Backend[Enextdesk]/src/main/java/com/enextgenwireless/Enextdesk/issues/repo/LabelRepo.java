package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface LabelRepo extends JpaRepository<Label, Long> {

    @Query("select u from Label u where lower(u.name) like lower(concat('%', ?1,'%'))")
    Set<Label> findByNameLikeIgnoreCase(String name);
}