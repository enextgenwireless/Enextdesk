package com.enextgenwireless.Enextdesk.issues.domain;

import com.enextgenwireless.Enextdesk.AuditModel;
import com.enextgenwireless.Enextdesk.project.domain.CustomField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class IssueCustomField extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CustomField customField;

    private String value;

    @Deprecated(forRemoval = true)
    private String[] values;

}
