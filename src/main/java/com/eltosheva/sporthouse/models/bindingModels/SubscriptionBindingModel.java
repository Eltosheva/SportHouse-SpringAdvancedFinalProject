package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class SubscriptionBindingModel {
    @NotBlank(message = "Enter training plan name")
    @Size(min = 5, max = 20, message = "Subscription name must be between 5 and 20 characters.")
    private String name;
    @Positive(message = "Subscription price must be positive number.")
    private BigDecimal price;
    @Positive(message = "Training count must be positive number.")
    private Integer trainingCount;
    @NotNull(message = "Select start date for this subscription.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Start date must be present or future date.")
    private Date startDate;
    @NotNull(message = "Select end date for this subscription.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Expiration date must be present or future date.")
    private Date expireDate;
    private Boolean isActive;
}