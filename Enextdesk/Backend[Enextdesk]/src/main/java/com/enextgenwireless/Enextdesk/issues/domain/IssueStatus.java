package com.enextgenwireless.Enextdesk.issues.domain;

import com.enextgenwireless.Enextdesk.AuditModel;
import com.enextgenwireless.Enextdesk.auth.domain.Login;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class IssueStatus  extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private IssueLifeCycle issueLifeCycle;

    @NotNull
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "by")
    private Login by;

    @Transient
    private boolean editable, deletable;

    public IssueStatus(String name, @NotNull IssueLifeCycle issueLifeCycle) {
        this.name = name;
        this.issueLifeCycle = issueLifeCycle;
    }
}
