package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.TopicDto;

import java.util.List;

public interface TopicService {

    TopicDto findById(long id);
    List<TopicDto> findByConfId(long confId);
}
