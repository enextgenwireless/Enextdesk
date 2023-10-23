package com.enextgenwireless.Enextdesk.issues.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.issues.domain.Issue;
import com.enextgenwireless.Enextdesk.issues.domain.Watchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Set;

public interface WatchersRepo extends JpaRepository<Watchers, Long> {

    Set<Watchers> findByIssue(Issue issue);

    Set<Watchers> findByWatcher(Login watcher);

    Set<Watchers> findByIssueAndWatcher(Issue issue, Login watcher);

    @Transactional
    @Modifying
    void deleteByIssue(Issue issue);

}
