package com.advcourse.conferenceassistant.exception.handler;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.exception.NoSuchVisitorException;
import com.advcourse.conferenceassistant.exception.NotActiveConferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest() {

        return "badrequest-page";
    }
    @ExceptionHandler(NotActiveConferenceException.class)
    public String notActiveConfExceptionHandler() {

        return "conferenceError";
    }
}
