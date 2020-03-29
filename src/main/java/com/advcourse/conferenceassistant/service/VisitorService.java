package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService implements VisitorServiceImpl {
    @Autowired
    private VisitorRepository visitorRepository;


    /**
     *adding visitor to db
     * */
    @Override
    public VisitorDto registerNewVisitorDtoAccount(VisitorDto dto) {
        Visitor visitor = VisitorMapper.fromDto(dto);
        Visitor visitorByEmail = visitorRepository.findByEmail(dto.getEmail());

        if (visitorByEmail != null) {
            return dto;
        }

        return VisitorMapper.toDto(visitorRepository.save(visitor));


    }


}
