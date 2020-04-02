package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.service.dto.TopicDto;

public class TopicMapper {

    public static TopicDto toDto (Topic topic) {
        return new TopicDto(
                topic.getId(),
                topic.getTheme(),
                topic.getDescription(),
                topic.getSpeaker(),
                topic.getSpeakerimg(),
                topic.getSpeakerdesc(),
                topic.getStart(),
                topic.getEnd(),
                topic.getConf().getId()     // getConf() - this good???
        );
    }

    public static Topic fromDto(TopicDto dto) {
        Topic topic = new Topic();
        topic.setId(dto.getId());
        topic.setTheme(dto.getTheme());
        topic.setDescription(dto.getDescription());
        topic.setSpeaker(dto.getSpeaker());
        topic.setSpeakerimg(dto.getSpeakerimg());
        topic.setSpeakerdesc(dto.getSpeakerdesc());
        topic.setStart(dto.getStart());
        topic.setEnd(dto.getEnd());
        Conference conference = new Conference();
        conference.setId(dto.getConfId());
        topic.setConf(conference);

        return topic;
    }
}
