package com.enextgenwireless.Enextdesk.project.domain;

import com.enextgenwireless.Enextdesk.AuditModel;
import com.enextgenwireless.Enextdesk.project.service.ConfigurationService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EnextgenConfiguration extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfigurationService.JDCONFIG key;

    private String stringValue;

    private Long longValue;

    private boolean booleanValue;

    public EnextgenConfiguration(ConfigurationService.JDCONFIG key) {
        this.key = key;
    }

    public EnextgenConfiguration(ConfigurationService.JDCONFIG key, String stringValue) {
        this.key = key;
        this.stringValue = stringValue;
    }

    public EnextgenConfiguration(ConfigurationService.JDCONFIG key, Long longValue) {
        this.key = key;
        this.longValue = longValue;
    }

    public EnextgenConfiguration(ConfigurationService.JDCONFIG key, boolean booleanValue) {
        this.key = key;
        this.booleanValue = booleanValue;
    }
}
