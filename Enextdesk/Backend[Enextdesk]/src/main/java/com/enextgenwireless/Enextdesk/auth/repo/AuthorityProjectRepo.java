package com.enextgenwireless.Enextdesk.auth.repo;


import com.enextgenwireless.Enextdesk.auth.domain.AuthorityCode;
import com.enextgenwireless.Enextdesk.auth.domain.AuthorityProject;
import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorityProjectRepo extends JpaRepository<AuthorityProject, Long> {

    List<AuthorityProject> findByProjectAndLogin(Project p, Login l);

    List<AuthorityProject> findByProject(Project p);

    List<AuthorityProject> findByProjectAndAuthorityCode(Project p, AuthorityCode code);

    List<AuthorityProject> findByLogin(Login l);

    @Transactional
    @Modifying
    void deleteByLogin(Login l);

}
