package com.enextgenwireless.Enextdesk.auth.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TokenRepo extends JpaRepository<Token, String> {

    Token findByToken(String token);

    Set<Token> findAllByUser(Login l);
}