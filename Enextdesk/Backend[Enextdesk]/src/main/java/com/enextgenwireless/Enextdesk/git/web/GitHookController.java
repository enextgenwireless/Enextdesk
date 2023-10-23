package com.enextgenwireless.Enextdesk.git.web;

import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.git.service.GitHookService;
import com.enextgenwireless.Enextdesk.git.service.GitRepositoryService;
import com.enextgenwireless.Enextdesk.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = SystemInfo.apiPrefix + "/hook/git/", produces = "application/json", consumes = "application/json")
public class GitHookController {

    @Autowired
    private GitRepositoryService gitRepositoryService;
    @Autowired
    private GitHookService gitHookService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("{uuid}")
    public void receiveHooks(@PathVariable("uuid") String repoId, @RequestBody String payload,
                             HttpServletRequest request) {
        gitHookService.receiveHook(repoId, payload, request);
    }

}
