package com.eltosheva.sporthouse.models.bindingModels;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProfileBindingModel {
    private String id;
    @Size(min = 3, max = 20, message = "Firstname must be between 3 and 20 characters.")
    @NotBlank
    private String firstName;
    @Size(min = 3, max = 20, message = "Lastname must be between 3 and 20 characters.")
    @NotBlank
    private String lastName;
    @Size(max = 20, message = "Password must be between 3 and 20 characters.")
    private String password;
    @Size(max = 20, message = "Confirm password must be between 3 and 20 characters.")
    private String confirmPassword;
    @Size( max = 20, message = "Password must be between 3 and 20 characters.")
    private String newPassword;
    @Email(message = "Incorrect email address.")
    private String email;
    @Size(min = 8, message = "Phone number must be at least 8 numbers.")
    private String phoneNum;
    @URL
    private String profilePictureUrl;
}
