package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.UserOrdersServiceModel;

import java.util.List;

public interface UserOrderHistoryService {
    void createOrder();
    List<UserOrdersServiceModel> findAllUserOrders();
    List<UserOrdersServiceModel> findAllUserSubscriptions();
}
