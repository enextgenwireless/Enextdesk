package com.enextgenwireless.Enextdesk.event.domain;

import com.github.bohnman.squiggly.view.PropertyView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PropertyView("secret")
    private String hookurl;

    private EnextgenEventType type;

    private String projectKey;

    @PropertyView("secret")
    private Long loginID;

    public Subscription(String hookurl, EnextgenEventType type, String projectKey, Long loginID) {
        this.hookurl = hookurl;
        this.type = type;
        this.loginID = loginID;
        this.projectKey = projectKey;
    }
}
