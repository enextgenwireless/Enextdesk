package com.enextgenwireless.Enextdesk.auth.repo;


import com.enextgenwireless.Enextdesk.auth.domain.AuthorityCode;
import com.enextgenwireless.Enextdesk.auth.domain.AuthorityGlobal;
import com.enextgenwireless.Enextdesk.auth.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorityGlobalRepo extends JpaRepository<AuthorityGlobal, Long> {

    List<AuthorityGlobal> findByLogin(Login l);

    List<AuthorityGlobal> findByAuthorityCode(AuthorityCode code);

    @Transactional
    @Modifying
    void deleteByLogin(Login l);

}
