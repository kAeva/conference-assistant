package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class VisitorMapper {


    public static VisitorDto toDto(Visitor entity) {
        Set<Long> allConfId = entity.getVisit().stream().map(Conference::getId).collect(Collectors.toSet());

        return new VisitorDto(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                allConfId);
    }

    public static Visitor fromDto(VisitorDto visitorDto) {
        Set<Conference> conferences = new HashSet<>();
        Set<Long> confId = visitorDto.getConfId();
        for (Long id : confId) {
            Conference conference = new Conference();
            conference.setId(id);
            conferences.add(conference);
        }
        Visitor visitor = new Visitor();
        visitor.setId(visitorDto.getId());
        visitor.setEmail(visitorDto.getEmail());
//      get name from email using setName() method in Visitor
        visitor.setName(visitorDto.getEmail());
        visitor.setVisit(conferences);

        return visitor;
    }
}


