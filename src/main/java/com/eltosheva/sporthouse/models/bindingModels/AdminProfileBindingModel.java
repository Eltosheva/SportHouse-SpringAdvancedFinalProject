package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AdminProfileBindingModel extends ProfileBindingModel {
    @NotBlank
    private String sportId;
}