package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.issues.domain.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueStatusRepo extends JpaRepository<IssueStatus, Long> {

    IssueStatus findByName(String name);

}
