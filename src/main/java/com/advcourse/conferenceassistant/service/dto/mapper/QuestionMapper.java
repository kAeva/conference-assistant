package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Question;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuestionMapper {

    public static QuestionDto toDto(Question e, boolean isLiked, int liked) {
    return new QuestionDto(e.getId(),
            e.getQuestion(),
            e.getAuthor().getId(),
            e.getAuthor().getName(),
            e.getTopic().getId(),
            e.getTime(),
            isLiked,
            liked,
            e.isAnswerStatus());
}

    public static Question fromDto(QuestionDto dto) {
        log.debug("fromDto mapper dto received: " + dto);
        Question q = new Question();
        q.setQuestion(dto.getQuestion());
        log.debug("Set question text from dto: " + q.getQuestion());
        q.setTime(dto.getTime());
        log.debug("Set time from dto: " + q.getTime());
        q.setAnswerStatus(dto.getAnswerStatus());
        log.debug("Set statys drom dto: " + q.isAnswerStatus());
        Topic topic = new Topic();
        topic.setId(dto.getTopicId());
        q.setTopic(topic);
        Visitor visitor = new Visitor();
        visitor.setId(dto.getCreatorId());
        log.debug("Received Question from DTO: " + q);
        return q;
    }
}
