package com.enextgenwireless.Enextdesk.issues.domain;

import lombok.Data;

import java.util.List;

@Data
public class QuickUpdate {
    String field, data;
    List<String> issuesKeyPairs;
}
