package com.advcourse.conferenceassistant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionDto {

    private Long id;
    private String question;
    private long creatorId;
    private String creatorName;
    private Long topicId;
    private LocalDateTime time;
    private Boolean isLikedByThisVisitor;
    private Integer likesQuantity;
    private Boolean answerStatus;
}
