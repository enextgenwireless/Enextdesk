package com.enextgenwireless.Enextdesk.board.repo;

import com.enextgenwireless.Enextdesk.board.domain.Board;
import com.enextgenwireless.Enextdesk.issues.domain.IssueFilter;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BoardRepo extends JpaRepository<Board, Long> {

    Set<Board> findAllByProject(Project project);

    Set<Board> findAllByFilter(IssueFilter filter);

    Set<Board> findAllByProjectAndActiveTrue(Project project);
}