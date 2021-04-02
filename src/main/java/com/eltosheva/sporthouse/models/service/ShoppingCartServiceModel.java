package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShoppingCartServiceModel {
    private String id;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime addedAt;
    private User user;
    private String productId;
    private String name;
    private String imageUrl;
}
