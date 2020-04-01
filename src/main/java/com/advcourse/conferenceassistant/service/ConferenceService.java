package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.ConferenceDto;


public interface ConferenceService {
    ConferenceDto findById(Long id);
}
