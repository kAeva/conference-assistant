package com.advcourse.conferenceassistant.exception.handler;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.exception.NoSuchVisitorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchConferenceException.class)
    public String noSuchConfExceptionHandler() {

        return "conferenceError";
    }

    @ExceptionHandler(NoSuchVisitorException.class)
    public String noSuchVisitorExceptionHandler() {

        return "visitorError";
    }

}
