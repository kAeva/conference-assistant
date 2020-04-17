package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.NoSuchVisitorException;
import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.dto.mapper.ConferenceMapper;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ConferenceServiceImpl conferenceService;

    /**
     * adding visitor to db
     */
    @Override
    public VisitorDto registerNewVisitorDtoAccount(VisitorDto dto) {
        Visitor visitor = VisitorMapper.fromDto(dto);
        Visitor visitorByEmail = visitorRepository.findByEmail(dto.getEmail());
        if (visitorByEmail != null) {
            visitor = VisitorMapper.fromDto(addConference(VisitorMapper.toDto(visitorByEmail), dto.getConfId()));
        }
        return VisitorMapper.toDto(visitorRepository.save(visitor));
    }

    @Override
    public VisitorDto findByEmailAndVisit(String email, Long conf_id) {

        Conference conference = ConferenceMapper.fromDto(conferenceService.findById(conf_id));

        Visitor byEmailAndVisit = visitorRepository.findByEmailAndVisit(email, conference);
        if (byEmailAndVisit == null) {
            throw new NoSuchVisitorException();
        }

        return VisitorMapper.toDto(byEmailAndVisit);
    }

    @Override
    public VisitorDto findByEmail(String email) {
        Visitor visitor = visitorRepository.findByEmail(email);
        if (visitor == null) {
            throw new NoSuchVisitorException();
        }
        return VisitorMapper.toDto(visitor);
    }


    @Override
    public VisitorDto findById(Long id) {
        try {
            return VisitorMapper.toDto(visitorRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            throw new NoSuchVisitorException();
        }
    }

    @Override
    public VisitorDto addConference(VisitorDto visitorDto, Set<Long> confIDSet) {
        visitorDto.getConfId().addAll(confIDSet);
        return visitorDto;
    }

    @Override
    public boolean isVisitorHasConferenceId(String email, Long conf_id) {
        Visitor visitor = visitorRepository.findByEmail(email);
        return visitor != null && VisitorMapper.toDto(visitor).getConfId().contains(conf_id);


    }
}