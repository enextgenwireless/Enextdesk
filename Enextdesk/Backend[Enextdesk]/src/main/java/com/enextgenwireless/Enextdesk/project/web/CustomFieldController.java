package com.enextgenwireless.Enextdesk.project.web;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.issues.service.VersionService;
import com.enextgenwireless.Enextdesk.project.domain.CustomField;
import com.enextgenwireless.Enextdesk.project.service.CustomFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = SystemInfo.apiPrefix + "/manage/project/{project_key}/custom-field", produces = "application/json", consumes = "application/json")
public class CustomFieldController {

    @Autowired
    private CustomFieldService customFieldService;

    @Autowired
    private VersionService versionService;

    @GetMapping("/")
    public String getAll(@PathVariable("project_key") String projectKey) {
        return HelperUtil.squiggly("-user_detail,customfield_detail", customFieldService.getAllForProject(projectKey));
    }

    @GetMapping("/{custom_field_id}")
    public String get(@PathVariable("project_key") String projectKey, @PathVariable("custom_field_id") Long fieldId) {
        return HelperUtil.squiggly("-user_details,customfield_detail", customFieldService.get(projectKey, fieldId));
    }

    @PostMapping("/save")
    public String save(@PathVariable("project_key") String projectKey, @RequestBody CustomField customField) {
        return HelperUtil.squiggly("base,customfield_detail", customFieldService.save(projectKey, customField));
    }

    @PostMapping("/delete")
    public void delete(@PathVariable("project_key") String projectKey, @RequestBody CustomField customField) {
        customFieldService.delete(projectKey, customField);
    }
}
