package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {

    private Long id;
    private String theme;
    private String description;
    private String speaker;
    private String speakerimg;
    private String speakerdesc;
    private LocalDateTime start;
    private LocalDateTime end;
}
