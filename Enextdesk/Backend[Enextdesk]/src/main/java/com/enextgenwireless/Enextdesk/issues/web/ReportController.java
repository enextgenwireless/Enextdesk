package com.enextgenwireless.Enextdesk.issues.web;

import com.enextgenwireless.Enextdesk.HelperUtil;
import com.enextgenwireless.Enextdesk.SystemInfo;
import com.enextgenwireless.Enextdesk.issues.repo.ReportDTO;
import com.enextgenwireless.Enextdesk.issues.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(produces = "application/json", value = SystemInfo.apiPrefix + "/report/{project_key}")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getReport(@PathVariable("project_key") String projectKey) {
        return HelperUtil.squiggly("base", reportService.getReport(projectKey));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String getReportData(@PathVariable("project_key") String projectKey, @RequestBody ReportDTO reportDTO) throws ParseException {
        return HelperUtil.squiggly("base", reportService.getReportData(projectKey, reportDTO));
    }
}
