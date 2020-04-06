package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.repository.TopicRepository;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public TopicDto findById(long id) {

        return TopicMapper.toDto(topicRepository.findById(id).get());

    }

    @Override
    public List<TopicDto> findByConfId(long confId) {
        return TopicMapper.toDtos(topicRepository.findByConferenceId(confId));
    }


}
