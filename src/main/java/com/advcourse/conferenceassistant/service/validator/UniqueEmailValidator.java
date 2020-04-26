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
     *Checks whether or not a given Staff email exists in the database
     *
     * @param email The email to check for
     * @return true if Staff with this email not exists in the database
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !staffService.isActiveUser(email);
    }

}

