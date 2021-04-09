package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.DecimalMin;

@Data
@NoArgsConstructor
public class SportsmanRegisterBindingModel extends UserRegisterBindingModel {
    @NonNull
    private Integer age;
    @DecimalMin("0")
    private Double targetWeight;
}
