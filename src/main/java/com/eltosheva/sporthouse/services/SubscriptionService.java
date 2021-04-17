package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.SubscriptionServiceModel;

import java.util.List;

public interface SubscriptionService {
    void addEditSubscription(SubscriptionServiceModel subscriptionServiceModel);
    List<SubscriptionServiceModel> getAllSubscriptions();
    void changeStatus(String id);
}