package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class SportsmanRegisterBindingModel extends UserRegisterBindingModel {
    @NotNull(message = "Age can not be null.")
    private Integer age;
    @NotNull(message = "Target weight can not be null.")
    @Positive(message = "Must be positive number.")
    private Double targetWeight;
}
