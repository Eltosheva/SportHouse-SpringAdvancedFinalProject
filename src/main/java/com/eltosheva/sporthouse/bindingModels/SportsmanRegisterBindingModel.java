package com.eltosheva.sporthouse.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
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
