package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductBindingModel {
    private String productId;
    @NotBlank(message = "Enter product name.")
    @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters.")
    private String productName;
    @NotNull(message = "Enter product price.")
    @Positive(message = "Product price must be a positive number.")
    private BigDecimal productPrice;
    @URL(message = "You must provide a valid URL address.")
    private String productImageUrl;
    @NotBlank(message = "Description can not be empty")
    @Size(min = 10, message = "Description can not be less than 10 characters.")
    private String productDescription;
    @NotNull(message = "Enter available product quantity.")
    @Positive(message = "Available product quantity must be a positive number.")
    private Integer productAvailableQuantity;
    @NotBlank(message = "The item must have sport type")
    private String productSportId;
    @NotNull(message = "Select product status.")
    private Boolean isActive;
}
