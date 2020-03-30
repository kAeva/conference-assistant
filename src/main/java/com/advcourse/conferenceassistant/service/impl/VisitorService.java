package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.VisitorServiceImpl;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService implements VisitorServiceImpl {
    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    /**
     * adding visitor to db
     */
    @Override
    public VisitorDto registerNewVisitorDtoAccount(VisitorDto dto) {
        Visitor visitor = VisitorMapper.fromDto(dto);
        Conference conference = conferenceRepository.findById(dto.getConfId()).get();
        Visitor visitorByEmail = visitorRepository.findByEmailAndVisit(dto.getEmail(), conference);

        if (visitorByEmail != null) {
            return dto;
        }

        return VisitorMapper.toDto(visitorRepository.save(visitor));


    }


}