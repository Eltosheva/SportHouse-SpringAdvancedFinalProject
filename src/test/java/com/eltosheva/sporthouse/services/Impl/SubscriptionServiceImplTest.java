package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.repositories.SubscriptionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    private SubscriptionServiceImpl subscriptionService;
    private ModelMapper modelMapper;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, productService, modelMapper);
    }

    @Test
    void addNewSubscription() {
    }

    @Test
    void activityStatus() {

    }

    @Test
    void getAllSubscriptions() {

    }

    @Test
    @WithMockUser(value = "1@1")
    void changeStatus_validTest() {
        Subscription sub = new Subscription();
        sub.setName("TET");
        sub.setIsActive(true);
        sub.setId("1232");
        when(subscriptionRepository.findById("1232")).thenReturn(Optional.ofNullable(sub));
        subscriptionService.changeStatus("1232");
    }

    @Test
    @WithMockUser(value = "1@1")
    void changeStatus_invalidTest() {
        Subscription sub = new Subscription();
        sub.setName("TET");
        sub.setIsActive(true);
        sub.setId("1232");
        when(subscriptionRepository.findById("1232")).thenReturn(Optional.ofNullable(sub));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> subscriptionService.changeStatus("1111"));
    }
}