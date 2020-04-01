package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService {
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

    @Override
    public VisitorDto findByEmailAndVisit(String email, Long conf_id) {
        Conference conference = conferenceRepository.findById(conf_id).get();
        return VisitorMapper.toDto(visitorRepository.findByEmailAndVisit(email,conference));
    }

    @Override
    public VisitorDto findById(Long id) {
       return VisitorMapper.toDto(visitorRepository.findById(id).get());

    }


}