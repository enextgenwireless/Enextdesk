package com.enextgenwireless.Enextdesk.webhook.web;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.event.domain.EnextgenEventType;
import com.enextgenwireless.Enextdesk.webhook.domain.WebHook;
import com.enextgenwireless.Enextdesk.webhook.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = SystemInfo.apiPrefix + "/manage/webhook", produces = "application/json", consumes = "application/json")
public class WebHookController {

    @Autowired
    private WebHookService webHookService;

    @GetMapping("/")
    public String getAll() {
        return HelperUtil.squiggly("-user_detail", webHookService.getAllHooks());
    }

    @GetMapping("{id}")
    public String get(@PathVariable("id") Long id) {
        return HelperUtil.squiggly("-user_details", webHookService.getHook(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "save")
    public String save(@RequestBody WebHook v) {
        return HelperUtil.squiggly("base,-user_detail", webHookService.save(v));
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public void remove(@RequestBody WebHook v) {
        webHookService.delete(v);
    }

    @GetMapping("/eventTypes")
    public String getEventTypes() {
        return HelperUtil.squiggly("-user_detail", EnextgenEventType.values());
    }
}
