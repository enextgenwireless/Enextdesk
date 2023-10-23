package com.enextgenwireless.Enextdesk.project.service;

import com.enextgenwireless.Enextdesk.project.domain.EnextgenConfiguration;
import com.enextgenwireless.Enextdesk.project.repo.JDConfigurationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Value("${app.domain}")
    private String appDomain;
    @Autowired
    private JDConfigurationRepo jdConfigurationRepo;

    @Cacheable(value = "configuration", key = "#jdc")
    public String getString(JDCONFIG jdc) {
        EnextgenConfiguration v = jdConfigurationRepo.findByKey(jdc);
        return v != null ? v.getStringValue() : "";
    }

    @Cacheable(value = "configuration", key = "#jdc")
    public boolean getBoolean(JDCONFIG jdc) {
        EnextgenConfiguration v = jdConfigurationRepo.findByKey(jdc);
        return v != null && v.isBooleanValue();
    }

    @Cacheable(value = "configuration", key = "#jdc")
    public Long getLong(JDCONFIG jdc) {
        return jdConfigurationRepo.findByKey(jdc).getLongValue();
    }

    @CacheEvict(value = "configuration", allEntries = true)
    public void save(JDCONFIG jdc, String value) {
        EnextgenConfiguration jdconfig = jdConfigurationRepo.findByKey(jdc);
        if (jdconfig == null) {
            jdconfig = new EnextgenConfiguration(jdc);
        }
        jdconfig.setStringValue(value);
        jdConfigurationRepo.save(jdconfig);
    }

    @CacheEvict(value = "configuration", allEntries = true)
    public void saveBoolean(JDCONFIG jdc, boolean value) {
        EnextgenConfiguration jdconfig = jdConfigurationRepo.findByKey(jdc);
        if (jdconfig == null) {
            jdconfig = new EnextgenConfiguration(jdc);
        }
        jdconfig.setBooleanValue(value);
        jdConfigurationRepo.save(jdconfig);
    }

    public String getApplicationDomain() {
        return getString(JDCONFIG.APP_BASE_URL);
    }

    public enum JDCONFIG {
        APP_TIMEZONE,
        APP_BASE_URL,
        APP_BUSINESS_START_TIME,
        APP_BUSINESS_END_TIME,
        APP_SLACK_ENABLED,
        APP_SLACK_BOT_ID,
        APP_SLACK_ACCESS_TOKEN,
        APP_SLACK_TEAM_ID,
        APP_SLACK_TEAM_NAME,
        APP_SLACK_FAILURE,
        APP_SETUP,
        APP_SELF_REGISTRATION_EMAIL_ENABLED,
        APP_SELF_REGISTRATION_SLACK_ENABLED,
        APP_SELF_REGISTRATION_ALLOWED_DOMAINS,
        APP_SELF_REGISTRATION_REQUIRE_REVIEW_EMAIL,
        APP_SELF_REGISTRATION_REQUIRE_REVIEW_SLACK
    }
}
