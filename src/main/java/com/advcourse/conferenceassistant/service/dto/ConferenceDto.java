package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceDto {

    private Long id;
    private String theme;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String address;
}
