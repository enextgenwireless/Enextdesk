package com.enextgenwireless.Enextdesk.wiki.repo;

import com.enextgenwireless.Enextdesk.project.domain.Project;
import com.enextgenwireless.Enextdesk.wiki.domain.WikiFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WikiFolderRepo extends JpaRepository<WikiFolder, Long> {

    Set<WikiFolder> findByProjectOrderByTitleAsc(Project project);

    Set<WikiFolder> findByProjectAndParentOrderByTitleAsc(Project project, WikiFolder parent);

    int countByProjectAndParent(Project project, WikiFolder parent);

    Set<WikiFolder> findTop10ByProjectAndTitleIgnoreCaseContainingOrderByTitleAsc(Project project, String title);

    WikiFolder findByProjectAndProjectDefaultTrue(Project project);
}
