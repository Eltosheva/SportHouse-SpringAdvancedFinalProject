package com.eltosheva.sporthouse.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ShoppingCartBindingModel {
    @NotEmpty
    private String productId;
    @NonNull
    private Integer quantity;
}
