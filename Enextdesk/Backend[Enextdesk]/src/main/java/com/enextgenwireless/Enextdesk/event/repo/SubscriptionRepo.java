package com.enextgenwireless.Enextdesk.event.repo;

import com.enextgenwireless.Enextdesk.event.domain.EnextgenEventType;
import com.enextgenwireless.Enextdesk.event.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    List<Subscription> findByLoginID(Long loginID);

    List<Subscription> findByLoginIDAndTypeAndProjectKey(Long loginID, EnextgenEventType type, String projectKey);

    List<Subscription> findByTypeAndProjectKey(EnextgenEventType type, String projectKey);

    Subscription findByIdAndTypeAndProjectKey(Long id, EnextgenEventType type, String projectKey);

}