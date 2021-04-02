package com.eltosheva.sporthouse.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductBindingModel {
    private String productId;
    @NotBlank
    private String productName;
    @NonNull
    private BigDecimal productPrice;
    private String productImageUrl;
    private String productDescription;
    @NonNull
    private Integer productAvailableQuantity;
    @NotBlank(message = "The item must have sport type")
    private String productSportId;
    private Boolean isActive;
}
