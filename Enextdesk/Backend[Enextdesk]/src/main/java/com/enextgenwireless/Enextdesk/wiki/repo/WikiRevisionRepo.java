package com.enextgenwireless.Enextdesk.wiki.repo;

import com.enextgenwireless.Enextdesk.wiki.domain.Wiki;
import com.enextgenwireless.Enextdesk.wiki.domain.WikiRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface WikiRevisionRepo extends JpaRepository<WikiRevision, Long> {

    Set<WikiRevision> findByWikiOrderByIdDesc(Wiki wiki);

    WikiRevision findByWikiAndVersion(Wiki wiki, Long version);

    @Query(value = "SELECT MAX(VERSION) FROM WIKI_REVISION WHERE WIKI = ?1", nativeQuery = true)
    Long findLastVersionForWiki(Wiki wiki);
}
