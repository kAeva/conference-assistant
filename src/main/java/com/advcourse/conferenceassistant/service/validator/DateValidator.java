package com.advcourse.conferenceassistant.service.validator;

import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class DateValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {

        return ConferenceDto.class.isAssignableFrom(aClass) && TopicDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LocalDateTime start;
        LocalDateTime end;
        if (o instanceof ConferenceDto) {
            start =  ((ConferenceDto) o).getStart();
            end =  ((ConferenceDto) o).getEnd();
        }else{
            start =  ((TopicDto) o).getStart();
            end =  ((TopicDto) o).getEnd();
        }
            if (start == null || end == null) {
                errors.rejectValue("end", "end", "End or start date shouldn't be empty");

            } else {
                if (end.isBefore(start) || end.isEqual(start)) {
                    errors.rejectValue("end", "end", "End date should be greater than start date");
                }
            }



    }
}
