package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ShoppingCartServiceModel;

import java.util.List;

public interface ShoppingCartService {
    void addProductToCart(ShoppingCartServiceModel shoppingCartServiceModel);
    List<ShoppingCartServiceModel> getAllUserProductsFromCart();
    void removeProductById(String id);
}
