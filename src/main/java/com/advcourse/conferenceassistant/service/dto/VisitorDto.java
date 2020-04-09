package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {

    private Long id;

    @NotNull
    @NotBlank(message = "Email shouldn't be empty")
    private String email;
    private String userName;
    private Set<Long> confId;

}
