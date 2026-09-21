package com.klu.subscription;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.klu.subscription.client.MemberClient;
import com.klu.subscription.entity.Subscription;
import com.klu.subscription.repository.SubscriptionRepository;
import com.klu.subscription.service.SubscriptionService;

class SubscriptionServiceApplicationTests {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MemberClient memberClient;

    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subscriptionService =
                new SubscriptionService(subscriptionRepository, memberClient);
    }

    @Test
    void testGetAllSubscriptions() {

        Subscription s1 =
                new Subscription(1L, "Monthly", "2026-09-01",
                        "2026-09-30", "ACTIVE");

        Subscription s2 =
                new Subscription(2L, "Yearly", "2026-09-01",
                        "2027-08-31", "ACTIVE");

        when(subscriptionRepository.findAll())
                .thenReturn(Arrays.asList(s1, s2));

        List<Subscription> result =
                subscriptionService.getAllSubscriptions();

        assertEquals(2, result.size());
        assertEquals("Monthly", result.get(0).getPlan());

        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    void testGetSubscriptionById() {

        Subscription subscription =
                new Subscription(1L, "Monthly", "2026-09-01",
                        "2026-09-30", "ACTIVE");

        when(subscriptionRepository.findById(1L))
                .thenReturn(Optional.of(subscription));

        Subscription result =
                subscriptionService.getSubscriptionById(1L);

        assertNotNull(result);
        assertEquals("Monthly", result.getPlan());

        verify(subscriptionRepository, times(1))
                .findById(1L);
    }

    @Test
    void testCreateSubscription() {

        Subscription subscription =
                new Subscription(1L, "Monthly", "2026-09-01",
                        "2026-09-30", "ACTIVE");

        when(subscriptionRepository.save(subscription))
                .thenReturn(subscription);

        Subscription result =
                subscriptionService.createSubscription(subscription);

        assertNotNull(result);
        assertEquals(1L, result.getMemberId());
        assertEquals("ACTIVE", result.getStatus());

        verify(subscriptionRepository, times(1))
                .save(subscription);
    }

    @Test
    void testUpdateSubscription() {

        Subscription existing =
                new Subscription(1L, "Monthly", "2026-09-01",
                        "2026-09-30", "ACTIVE");

        Subscription updated =
                new Subscription(2L, "Yearly", "2026-09-01",
                        "2027-08-31", "ACTIVE");

        when(subscriptionRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(subscriptionRepository.save(existing))
                .thenReturn(existing);

        Subscription result =
                subscriptionService.updateSubscription(1L, updated);

        assertNotNull(result);
        assertEquals(2L, result.getMemberId());
        assertEquals("Yearly", result.getPlan());
        assertEquals("2027-08-31", result.getEndDate());

        verify(subscriptionRepository, times(1))
                .findById(1L);

        verify(subscriptionRepository, times(1))
                .save(existing);
    }

    @Test
    void testDeleteSubscription() {

        doNothing()
                .when(subscriptionRepository)
                .deleteById(1L);

        subscriptionService.deleteSubscription(1L);

        verify(subscriptionRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void testGetMemberDetails() {

        com.klu.subscription.dto.MemberResponse member =
                new com.klu.subscription.dto.MemberResponse();

        member.setId(1L);
        member.setName("Mohit");
        member.setEmail("mohit@gmail.com");
        member.setPhone("9876543210");

        when(memberClient.getMemberById(1L))
                .thenReturn(member);

        com.klu.subscription.dto.MemberResponse result =
                subscriptionService.getMemberDetails(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mohit", result.getName());

        verify(memberClient, times(1))
                .getMemberById(1L);
    }
}