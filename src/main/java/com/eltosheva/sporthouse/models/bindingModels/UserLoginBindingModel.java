package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserLoginBindingModel {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 3 and 20 characters.")
    private String password;
}