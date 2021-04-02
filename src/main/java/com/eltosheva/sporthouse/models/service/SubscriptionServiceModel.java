package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SubscriptionServiceModel {
    private String id;
    private String name;
    private BigDecimal price;
    private Integer trainingCount;
    private Date startDate;
    private Date expireDate;
    private boolean isActive;
    private Set<User> users = new HashSet<>();
}
