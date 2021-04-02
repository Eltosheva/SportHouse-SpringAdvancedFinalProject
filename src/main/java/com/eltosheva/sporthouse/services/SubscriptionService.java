package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.SubscriptionServiceModel;

import java.util.List;

public interface SubscriptionService {
    void addNewSubscription(SubscriptionServiceModel subscriptionServiceModel);
    void activityStatus(String id);
    List<SubscriptionServiceModel> getAllSubscriptions();
//    List<SubscriptionServiceModel> getTopThree();
}
