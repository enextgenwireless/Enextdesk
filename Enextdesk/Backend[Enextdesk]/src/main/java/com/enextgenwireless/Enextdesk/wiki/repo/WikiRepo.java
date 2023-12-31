package com.enextgenwireless.Enextdesk.wiki.repo;

import com.enextgenwireless.Enextdesk.project.domain.Project;
import com.enextgenwireless.Enextdesk.wiki.domain.Wiki;
import com.enextgenwireless.Enextdesk.wiki.domain.WikiFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface WikiRepo extends JpaRepository<Wiki, Long> {

    Set<Wiki> findFirst10ByProjectOrderByCreatedDesc(Project project);

    Set<Wiki> findFirst10ByProjectOrderByUpdatedDesc(Project project);

    Set<Wiki> findByProjectOrderByTitleAsc(Project project);

    Set<Wiki> findByProjectAndFolderOrderByTitleAsc(Project project, WikiFolder folder);

    @Query(value = "Select * from wiki where project = :project AND CONCAT ( LOWER (title), LOWER (content) ) LIKE  %:query%", nativeQuery = true)
    Set<Wiki> findByContentContainingOrTitleContaining(@Param("query") String q, @Param("project") Long projectID);
}
