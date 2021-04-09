package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ShoppingCartBindingModel {
    @NotEmpty
    private String productId;
    @NonNull
    private Integer quantity;
}
