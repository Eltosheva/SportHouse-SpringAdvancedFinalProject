package com.eltosheva.sporthouse.validation;

import com.eltosheva.sporthouse.models.service.CoachServiceModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterCoachValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CoachServiceModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CoachServiceModel coachServiceModel = (CoachServiceModel) o;

    }
}
