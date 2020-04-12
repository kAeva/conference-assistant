package com.advcourse.conferenceassistant.service.validator;

import com.advcourse.conferenceassistant.service.StaffService;
import com.advcourse.conferenceassistant.service.validator.annotation.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private StaffService staffService;

    /**
     * if return true then -> "bindingResult.hasErrors() = true"
     * */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !staffService.isActiveUser(value);
    }

}

