package com.advcourse.conferenceassistant.service.validator.annotation;

import com.advcourse.conferenceassistant.service.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * UniqueEmailValidator.class is implementing the constraint for validation.
 * Annotation will be available after the runtime.
 * Annotation can be applied, i.e. on a field.
 * */
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueEmail {

    public String message() default "There is already user with this email!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
