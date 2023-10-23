package com.enextgenwireless.Enextdesk.page.repo;

import com.enextgenwireless.Enextdesk.page.domain.Page;
import com.enextgenwireless.Enextdesk.page.domain.PageRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Set;

public interface PageRevisionRepo extends JpaRepository<PageRevision, Long> {

    Set<PageRevision> findByPageOrderByIdDesc(Page wiki);

    @Transactional
    @Modifying
    void deleteByPage(Page wiki);

    PageRevision findByPageAndVersion(Page wiki, Long version);

    @Query(value = "SELECT MAX(VERSION) FROM page_revision WHERE PAGE = ?1", nativeQuery = true)
    Long findLastVersionForPage(Page wiki);
}
