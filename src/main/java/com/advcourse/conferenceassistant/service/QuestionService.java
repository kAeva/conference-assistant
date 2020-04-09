package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import java.util.List;

public interface QuestionService {
    QuestionDto getQuestionById(long id, String email);
    QuestionDto addQuestion(QuestionDto dto);
    List<QuestionDto> getQuestionsByTopicId(long topicId, String email);
    List<QuestionDto> getTopQuestionsByTopicId(long topicId, String email);
    QuestionDto like(long questionId, long guestId);
}
