package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductStoreServiceModel {
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String sportId;
    private char type;
}
