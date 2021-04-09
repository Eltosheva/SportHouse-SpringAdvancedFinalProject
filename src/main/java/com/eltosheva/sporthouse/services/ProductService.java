package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.ProductStoreServiceModel;

import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);
    List<ProductServiceModel> getAllProducts();
    List<ProductStoreServiceModel> getAllStoreProducts();
    void addNewSubscriptionTypeProduct(Subscription subscription);
    ProductServiceModel findById(String id);
    List<ProductServiceModel> getTopThree();
    void changeStatus(String id);
}
