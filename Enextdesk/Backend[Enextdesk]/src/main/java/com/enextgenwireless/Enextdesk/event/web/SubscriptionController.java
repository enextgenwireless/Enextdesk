package com.enextgenwireless.Enextdesk.event.web;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.auth.util.CurrentLogin;
import com.enextgenwireless.Enextdesk.event.domain.EnextgenEventType;
import com.enextgenwireless.Enextdesk.event.domain.Subscription;
import com.enextgenwireless.Enextdesk.event.repo.SubscriptionRepo;
import com.enextgenwireless.Enextdesk.issues.service.IssueService;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Log
@RequestMapping(produces = "application/json", value = SystemInfo.apiPrefix + "/hook/subscription/")
public class SubscriptionController {
    @Autowired
    CurrentLogin currentLogin;
    @Autowired
    private IssueService issueService;
    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String getSubscriptions(@RequestParam("project_key") String projectKey,
                                   @RequestParam("type") String type) {
        return HelperUtil.squiggly("base",
                subscriptionRepo.findByLoginIDAndTypeAndProjectKey(currentLogin.getUser().getId(),
                        EnextgenEventType.valueOf(type), projectKey));
    }

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public String createSubscriptions(@RequestParam("project_key") String projectKey,
                                      @RequestBody String data) {
        log.info("Subscription added");
        JSONObject d = new JSONObject(data);
        log.info(d.toString(2));
        return HelperUtil.squiggly("base", subscriptionRepo.save(new Subscription(d.getString("hookUrl"),
                EnextgenEventType.valueOf(d.getString("type")), projectKey, currentLogin.getUser().getId())));
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String removeSubscriptions(@RequestParam("project_key") String projectKey,
                                      @RequestBody String data) {
        JSONObject d = new JSONObject(data);
        Optional<Subscription> s = Optional.ofNullable(subscriptionRepo.findByIdAndTypeAndProjectKey(d.getLong("id"),
                EnextgenEventType.valueOf(d.getString("type")), projectKey));
        s.ifPresent(subscription -> subscriptionRepo.delete(subscription));
        log.info("Removed subscription");
        log.info(d.toString(2));
        return HelperUtil.squiggly("base", new JSONObject());
    }
}
