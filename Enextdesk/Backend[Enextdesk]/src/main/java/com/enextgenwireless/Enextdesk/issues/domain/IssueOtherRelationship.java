package com.enextgenwireless.Enextdesk.issues.domain;

import com.enextgenwireless.Enextdesk.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
public class IssueOtherRelationship extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "issue")
    private Issue issue;

    private String link, label;

}
