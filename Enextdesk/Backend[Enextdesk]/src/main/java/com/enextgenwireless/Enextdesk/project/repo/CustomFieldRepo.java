package com.enextgenwireless.Enextdesk.project.repo;

import com.enextgenwireless.Enextdesk.issues.domain.IssueType;
import com.enextgenwireless.Enextdesk.project.domain.CustomField;
import com.enextgenwireless.Enextdesk.project.domain.CustomFieldType;
import com.enextgenwireless.Enextdesk.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomFieldRepo extends JpaRepository<CustomField, Long> {

    Set<CustomField> findByNameOrderByNameAsc(String name);

    Set<CustomField> findByProjectOrderByNameAsc(Project project);

    Set<CustomField> findByProjectAndKeyOrderByNameAsc(Project project, String key);

    Set<CustomField> findByProjectAndIssueTypesOrderByNameAsc(Project project, IssueType issueType);

    Set<CustomField> findByTypeOrderByNameAsc(CustomFieldType type);

    CustomField findByProjectAndId(Project project, Long fieldId);
}
