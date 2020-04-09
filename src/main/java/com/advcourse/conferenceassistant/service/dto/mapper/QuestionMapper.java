package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Question;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuestionMapper {


    public static QuestionDto toDto(Question e, boolean isLikedByThisVisitor, int likesQuantity) {
//        log.info("this is the name we will transfer to questiondto " + e.getAuthor());
        return new QuestionDto(
                e.getId(),
                e.getQuestion(),
                e.getAuthor().getId(),
                e.getAuthor().getName(),
                e.getTopic().getId(),
                e.getTime(),
                isLikedByThisVisitor,
                likesQuantity);
    }

    public static Question fromDto(QuestionDto dto) {
        Question q = new Question();
        q.setQuestion(dto.getQuestionText());
        Topic topic = new Topic();
        topic.setId(dto.getTopicId());
        q.setTopic(topic);
        q.setTime(dto.getTime());
        Visitor visitor = new Visitor();
        visitor.setId(dto.getCreatorId());

        return q;
    }
}
