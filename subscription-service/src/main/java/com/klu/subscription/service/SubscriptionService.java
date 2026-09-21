package com.klu.subscription.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.subscription.client.MemberClient;
import com.klu.subscription.dto.MemberResponse;
import com.klu.subscription.entity.Subscription;
import com.klu.subscription.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MemberClient memberClient;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            MemberClient memberClient) {

        this.subscriptionRepository = subscriptionRepository;
        this.memberClient = memberClient;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Subscription existing = subscriptionRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setMemberId(subscription.getMemberId());
            existing.setPlan(subscription.getPlan());
            existing.setStartDate(subscription.getStartDate());
            existing.setEndDate(subscription.getEndDate());
            existing.setStatus(subscription.getStatus());

            return subscriptionRepository.save(existing);
        }

        return null;
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public MemberResponse getMemberDetails(Long memberId) {
        return memberClient.getMemberById(memberId);
    }
}