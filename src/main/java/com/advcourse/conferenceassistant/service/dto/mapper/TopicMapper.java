package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
public class TopicMapper {
    public static TopicDto toDto(Topic topic) {
        log.debug("Converting Topic to TopicDto: " + topic);
        return new TopicDto(
                topic.getId(),
                topic.getTheme(),
                topic.getDescription(),
                topic.getSpeaker(),
                topic.getSpeakerimg(),
                topic.getSpeakerdesc(),
                topic.getStart(),
                topic.getEnd(),
                topic.isEnded(),
                topic.getConference().getId()
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
        topic.setEnded(dto.getEnded());
        Conference conference = new Conference();
        conference.setId(dto.getConfId());
        topic.setConference(conference);

        return topic;
    }


    public static List<TopicDto> toDtos(List<Topic> entities) {
        return entities
                .stream()
                .map((e) -> new TopicDto(
                        e.getId(),
                        e.getTheme(),
                        e.getDescription(),
                        e.getSpeaker(),
                        e.getSpeakerimg(),
                        e.getSpeakerdesc(),
                        e.getStart(),
                        e.getEnd(),
                        e.getEnded(),
                        e.getConference().getId()))
                .collect(Collectors.toList());
    }

    public static List<Topic> fromDtos(List<TopicDto> dtos) {
        return dtos
                .stream()
                .map(TopicMapper::fromDto)
                .collect(Collectors.toList());
    }
}
