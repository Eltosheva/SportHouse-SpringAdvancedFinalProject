package com.eltosheva.sporthouse.models.service;


import com.eltosheva.sporthouse.models.entities.Sport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
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
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private Sport sport;
    private boolean isActive;
}
