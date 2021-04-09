package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.SubscriptionServiceModel;

import java.util.List;

public interface SubscriptionService {
    void addNewSubscription(SubscriptionServiceModel subscriptionServiceModel);
    List<SubscriptionServiceModel> getAllSubscriptions();
    void changeStatus(String id);
}
