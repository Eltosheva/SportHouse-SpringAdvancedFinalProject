package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);
    List<ProductServiceModel> getAllProducts();
    void addNewSubscriptionTypeProduct(Subscription subscription);
    ProductServiceModel findById(String id);
    List<ProductServiceModel> getTopThree();
}
