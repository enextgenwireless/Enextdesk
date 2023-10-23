package com.enextgenwireless.Enextdesk.board.web;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.board.domain.Board;
import com.enextgenwireless.Enextdesk.board.domain.Lane;
import com.enextgenwireless.Enextdesk.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json", consumes = "application/json",value = SystemInfo.apiPrefix)
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("project/{project_key}/board")
    public String getBoards(@PathVariable("project_key") String projectKey) {
        return HelperUtil.squiggly("base,-user_detail", boardService.getAllForProject(projectKey));
    }

    @GetMapping("project/{project_key}/board/{board_id}")
    public String getBoard(@PathVariable("project_key") String projectKey, @PathVariable("board_id") Long boardID) {
        return HelperUtil.squiggly("base,-user_detail", boardService.getBoard(boardID));
    }

    @PostMapping("project/{project_key}/board/")
    public String saveBoard(@PathVariable("project_key") String projectKey, @RequestBody Board board) {
        return HelperUtil.squiggly("base,-user_detail", boardService.saveBoard(projectKey, board));
    }

    @PostMapping("project/{project_key}/board/remove")
    public void deleteBoard(@PathVariable("project_key") String projectKey, @RequestBody Board board) {
        boardService.removeBoard(projectKey, board);
    }

    @GetMapping("project/{project_key}/board/{board_id}/lane/{lane_id}")
    public String getLane(@PathVariable("lane_id") Long laneID) {
        return HelperUtil.squiggly("base,-user_detail", boardService.getLane(laneID));
    }

    @PostMapping("project/{project_key}/board/{board_id}/lane/")
    public String saveLane(@PathVariable("board_id") Long boardID, @RequestBody Lane lane) {
        return HelperUtil.squiggly("base,-user_detail", boardService.saveLane(boardID, lane));
    }

    @PostMapping("project/{project_key}/board/{board_id}/lane/remove")
    public void deleteLane(@PathVariable("board_id") Long boardID, @RequestBody Lane lane) {
        boardService.removeLane(boardID, lane);
    }


}
