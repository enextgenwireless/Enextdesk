package com.enextgenwireless.Enextdesk.board.repo;

import com.enextgenwireless.Enextdesk.board.domain.Board;
import com.enextgenwireless.Enextdesk.board.domain.Lane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LaneRepo extends JpaRepository<Lane, Long> {
    Set<Lane> findAllByBoardOrderByLaneOrderAsc(Board board);

    int countByBoard(Board board);
}