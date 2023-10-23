package com.enextgenwireless.Enextdesk.page.repo;

import com.enextgenwireless.Enextdesk.page.domain.Page;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PageRepo extends JpaRepository<Page, Long> {

    Set<Page> findFirst10ByProjectOrderByCreatedDesc(Project project);

    Set<Page> findFirst10ByProjectOrderByUpdatedDesc(Project project);

    Set<Page> findByProjectOrderByTitleAsc(Project project);

    Set<Page> findByProjectAndParentOrderByTitleAsc(Project project, Page parent);

    long countByParent(Page parent);

    @Query(value = "Select * from wiki where project = :project AND CONCAT ( LOWER (title), LOWER (content) ) LIKE  %:query%", nativeQuery = true)
    Set<Page> findByContentContainingOrTitleContaining(@Param("query") String q, @Param("project") Long projectID);
}
