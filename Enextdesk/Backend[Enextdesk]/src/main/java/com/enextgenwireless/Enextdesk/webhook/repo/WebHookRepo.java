package com.enextgenwireless.Enextdesk.webhook.repo;

import com.enextgenwireless.Enextdesk.webhook.domain.WebHook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WebHookRepo extends JpaRepository<WebHook, Long> {
    Set<WebHook> findByActiveTrue();
}