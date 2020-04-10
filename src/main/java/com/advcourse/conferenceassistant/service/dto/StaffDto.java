package com.advcourse.conferenceassistant.service.dto;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.service.validator.annotation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
public class StaffDto {

    private Long id;

    @NotNull
    @NotBlank(message = "Email shouldn\''t be empty")
    @UniqueEmail
    private String email;
    private String name;
    private String surname;
    @NotBlank(message = "Password shouldn\''t be empty")
    private String pass;
    private Set<Long> colabs_id;

    private Set<Role> roles;

}
