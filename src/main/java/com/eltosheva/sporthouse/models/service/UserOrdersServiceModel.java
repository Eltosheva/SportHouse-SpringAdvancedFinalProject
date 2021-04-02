package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserOrdersServiceModel {
    Integer orderId;
    BigDecimal totalOrderPrice;
    List<UserOrderHistoryServiceModel> orderList;
    LocalDateTime orderDate;
}
