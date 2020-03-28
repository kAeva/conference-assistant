package com.advcourse.conferenceassistant.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private LibraryUserDetailService detailService;

    /**
     * if return true then -> "bindingResult.hasErrors() = true"
     * */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && detailService.isUsernameAlreadyInUse(value);
    }

}

