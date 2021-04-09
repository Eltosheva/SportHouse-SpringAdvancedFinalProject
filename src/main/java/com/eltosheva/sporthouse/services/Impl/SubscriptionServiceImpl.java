package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.models.service.SubscriptionServiceModel;
import com.eltosheva.sporthouse.repositories.SubscriptionRepository;
import com.eltosheva.sporthouse.services.ProductService;
import com.eltosheva.sporthouse.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void addNewSubscription(SubscriptionServiceModel subscriptionServiceModel) {
        Subscription subscription = modelMapper.map(subscriptionServiceModel, Subscription.class);
        subscription.setIsActive(true);
        subscriptionRepository.saveAndFlush(subscription);
        // todo add new product item for this subscription

        productService.addNewSubscriptionTypeProduct(subscription);
    }

    @Override
    public List<SubscriptionServiceModel> getAllSubscriptions() {
        List<SubscriptionServiceModel> subscriptionServiceModels = new ArrayList<>();
        subscriptionRepository
                .findAll()
                .stream()
                .forEach(subscription -> {
                    SubscriptionServiceModel subscriptionServiceModel = new SubscriptionServiceModel();
                    modelMapper.map(subscription, subscriptionServiceModel);
                    subscriptionServiceModels.add(subscriptionServiceModel);
                });
        return subscriptionServiceModels;
    }

    @Override
    public void changeStatus(String id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        subscription.setIsActive(!subscription.getIsActive());
        subscriptionRepository.saveAndFlush(subscription);
    }
}