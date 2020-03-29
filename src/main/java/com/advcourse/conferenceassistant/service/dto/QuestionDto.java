package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionDto {

    private Long id;
    private String questionText;
    private Long creatorId;
    private Long topicId;
    private LocalDateTime time;
    Boolean isLikedByThisVisitor;
    Integer likesQuantity;
}
