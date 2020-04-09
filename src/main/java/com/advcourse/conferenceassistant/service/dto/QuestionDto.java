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
    private String creatorName;
//    Todo: add a variable for creator Username - needs some changes into Visitor registering logic
    private Long topicId;
    private LocalDateTime time;
    Boolean isLikedByThisVisitor;
    Integer likesQuantity;
}
