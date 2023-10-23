package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.issues.domain.*;
import com.enextgenwireless.Enextdesk.issues.domain.IssueFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueFilterRepo extends JpaRepository<IssueFilter, Long> {

    List<IssueFilter> findByOwner(Login login);

    List<IssueFilter> findByOwnerOrOpenTrue(Login login);

    List<IssueFilter> findByOpenTrue();

}