package com.enextgenwireless.Enextdesk.webhook.service;

import com.enextgenwireless.Enextdesk.auth.service.AuthService;
import com.enextgenwireless.Enextdesk.exception.ErrorCode;
import com.enextgenwireless.Enextdesk.exception.JDException;
import com.enextgenwireless.Enextdesk.issues.service.IssueService;
import com.enextgenwireless.Enextdesk.webhook.domain.WebHook;
import com.enextgenwireless.Enextdesk.webhook.repo.WebHookLogRepo;
import com.enextgenwireless.Enextdesk.webhook.repo.WebHookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebHookService {

    @Autowired
    private WebHookRepo webHookRepo;
    @Autowired
    private IssueService issueService;
    @Autowired
    private AuthService authService;
    @Autowired
    private WebHookLogRepo webHookLogRepo;

    public List<WebHook> getAllHooks() {
        checkManageAccess();
        return webHookRepo.findAll();
    }

    public Optional<WebHook> getHook(Long id) {
        checkManageAccess();
        return webHookRepo.findById(id);
    }

    public WebHook save(WebHook webHook) {
        checkManageAccess();
        //If already exists
        if (webHook.getId() != null) {
            Optional<WebHook> webH = webHookRepo.findById(webHook.getId());
            if (webH.isPresent()) {
                webHook.setCreated(webH.get().getCreated());
                webHook.setCreatedBy(webH.get().getCreatedBy());
            }
        }
        webHook = webHookRepo.save(webHook);
        return webHook;
    }

    public void delete(WebHook webHook) {
        checkManageAccess();
        webHookLogRepo.deleteInBatch(webHookLogRepo.findByWebhookId(webHook.getId()));
        webHookRepo.delete(webHook);
    }

    public boolean hasManageAccess() {
        return authService.isSuperAdmin();
    }

    private void checkManageAccess() {
        if (!hasManageAccess()) throw new JDException("", ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN);
    }
}
