package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserOrderHistoryServiceModel {
    private Integer orderId;
    private BigDecimal price;
    private String name;
    private Integer quantity;
    private LocalDate date;
    private String imageUrl;
    private BigDecimal totalPrice;
    private LocalDateTime addedAt;
    private LocalDateTime orderDate;
    private User user;
    private String productId;
}
