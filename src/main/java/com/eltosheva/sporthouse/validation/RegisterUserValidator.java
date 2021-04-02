package com.eltosheva.sporthouse.validation;

import com.eltosheva.sporthouse.models.service.UserServiceModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserServiceModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}
