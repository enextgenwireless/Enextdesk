package com.enextgenwireless.Enextdesk.git.repo;

import com.enextgenwireless.Enextdesk.git.domain.GitHook;
import com.enextgenwireless.Enextdesk.git.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface HookRepo extends JpaRepository<GitHook, Long> {

    Set<GitHook> findAllByRepository(Repository repository);

}