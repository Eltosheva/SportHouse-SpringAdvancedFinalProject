package com.eltosheva.sporthouse.models.service;


import com.eltosheva.sporthouse.models.entities.Sport;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductServiceModel {
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private Integer availableQuantity;
    private String externalId;
    private Integer trainingCount;
    private Sport sport;
    private char type;
    private Boolean isActive;
}
