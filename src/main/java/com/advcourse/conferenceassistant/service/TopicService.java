package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.TopicDto;

import java.util.List;

public interface TopicService {

    TopicDto findById(long id);
    List<TopicDto> findByConfId(long confId);
    void deleteById(Long id);
    TopicDto save(TopicDto dto);
    TopicDto update(Long confId, TopicDto dto);
    TopicDto updateStartTime(Long topicId);
    TopicDto updateEndTime(Long topicId);
    TopicDto findActiveTopicByConfId(long confId);
 }
