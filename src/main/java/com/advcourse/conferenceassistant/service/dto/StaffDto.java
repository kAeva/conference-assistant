package com.advcourse.conferenceassistant.service.dto;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.service.validator.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffDto {

    private Long id;

    @UniqueEmail
    private String email;
    private String name;
    private String surname;
    private String pass;
    private Conference colab_id;

}
