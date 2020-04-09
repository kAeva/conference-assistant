package com.advcourse.conferenceassistant.converter;

public class EmailToUserNameConverter {
    String getNameFromEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }
}
