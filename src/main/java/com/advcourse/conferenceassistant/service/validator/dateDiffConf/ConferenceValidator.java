package com.advcourse.conferenceassistant.service.validator.dateDiffConf;


import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class ConferenceValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ConferenceDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ConferenceDto dto = (ConferenceDto) o;
        LocalDateTime start = dto.getStart();
        LocalDateTime end = dto.getEnd();

        if(start==null || end ==null){
            errors.rejectValue("end", "end","End or start date shouldn't be empty");

        }else {
            if (end.isBefore(start) || end.isEqual(start)) {
                errors.rejectValue("end", "end","End date should be greater than start date");
            }
        }


    }
}
