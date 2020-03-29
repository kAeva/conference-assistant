package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import org.springframework.beans.factory.annotation.Autowired;

public class VisitorMapper {




    public static VisitorDto toDto(Visitor entity) {
        return new VisitorDto(
                entity.getId(),
                entity.getEmail(),
                entity.getVisit().getId());
    }

    public static Visitor fromDto(VisitorDto visitorDto) {
        Visitor visitor = new Visitor();
        visitor.setId(visitorDto.getId());
        String email = visitorDto.getEmail();
        visitor.setEmail(email);
        visitor.setName(visitorDto.getEmail().substring(0, email.indexOf('@')));
        Conference conference = new Conference();
        conference.setId(visitorDto.getConfId());
        visitor.setVisit(conference);

        return visitor;
    }
}


