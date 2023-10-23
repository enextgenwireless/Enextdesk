package com.enextgenwireless.Enextdesk.wiki.repo;

import com.enextgenwireless.Enextdesk.wiki.domain.Wiki;
import com.enextgenwireless.Enextdesk.wiki.domain.WikiAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WikiAttachmentRepo extends JpaRepository<WikiAttachment, Long> {

    Set<WikiAttachment> findByWiki(Wiki wiki);

    WikiAttachment findByWikiAndId(Wiki wiki, Long id);

    WikiAttachment findByWikiAndOriginalName(Wiki wiki, String originalName);

    Set<WikiAttachment> findByWikiOrderByOriginalNameAsc(Wiki wiki);
}
