package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.VisitorDto;

import java.util.Set;


public interface VisitorService {
    VisitorDto registerNewVisitorDtoAccount(VisitorDto accountVisitor);

    VisitorDto findByEmailAndVisit(String email, Long conf_id);

    VisitorDto findByEmail(String email);

    VisitorDto findById(Long id);

    VisitorDto addConference(VisitorDto visitorDto, Set<Long> confIdSet);

    boolean isVisitorHasConferenceId(String email, Long conf_id);
}
