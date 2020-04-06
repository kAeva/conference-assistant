package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import java.util.List;

public interface QuestionService {
    QuestionDto getQuestionById(long id, String email);
    List<QuestionDto> getQuestionByTopicId(long topicId, String email);

    QuestionDto addQuestion(QuestionDto dto);
    QuestionDto like(long questionId, long guestId);
    String getCreatorName(QuestionDto qDto);
}
