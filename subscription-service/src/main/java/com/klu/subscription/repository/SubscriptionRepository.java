package com.klu.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.subscription.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}