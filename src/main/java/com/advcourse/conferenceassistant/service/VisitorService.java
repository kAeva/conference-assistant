package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;


public interface VisitorService {
    VisitorDto registerNewVisitorDtoAccount(VisitorDto accountVisitor);

    VisitorDto findByEmailAndVisit(String email, Long conf_id);

    VisitorDto findById(Long id);
}
