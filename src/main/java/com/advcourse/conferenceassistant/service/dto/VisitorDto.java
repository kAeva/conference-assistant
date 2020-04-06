package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {

    private Long id;

    @NotNull
    @NotBlank
    private String email;
    private Long confId;

}
