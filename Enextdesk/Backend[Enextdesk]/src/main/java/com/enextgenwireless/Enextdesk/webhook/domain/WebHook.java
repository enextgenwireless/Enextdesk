package com.enextgenwireless.Enextdesk.webhook.domain;

import com.enextgenwireless.Enextdesk.AuditModel;
import com.enextgenwireless.Enextdesk.event.domain.EnextgenEventType;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class WebHook extends AuditModel {

    @ElementCollection(targetClass = EnextgenEventType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "webhook_event_types")
    Set<EnextgenEventType> eventTypes = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String endPoint;
    private String query;
    @Column(columnDefinition = "boolean default false")
    private boolean active;
    @Column(columnDefinition = "boolean default false")
    private boolean allProjects, allEvents;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "webhook_request_headers", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns = {@JoinColumn(name = "whid")})
    private List<WebHookHeaders> requestHeaders = new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "webhook_projects", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns = {@JoinColumn(name = "whid")})
    private List<Project> projects = new ArrayList<>();
}
