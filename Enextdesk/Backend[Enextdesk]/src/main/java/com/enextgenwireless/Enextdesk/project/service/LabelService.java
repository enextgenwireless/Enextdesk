package com.enextgenwireless.Enextdesk.project.service;

import com.enextgenwireless.Enextdesk.issues.domain.Label;
import com.enextgenwireless.Enextdesk.issues.repo.LabelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepo labelRepo;

    @Cacheable(value = "label")
    public Optional<Label> get(Long id) {
        return labelRepo.findById(id);
    }
}
