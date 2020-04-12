package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;
    boolean ended;
    boolean isActive;
    private long confId;
}
