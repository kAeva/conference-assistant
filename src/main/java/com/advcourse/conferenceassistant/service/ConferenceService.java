package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.service.dto.ConferenceDto;

import java.util.List;


public interface ConferenceService {
    ConferenceDto findById(Long id);
    List<ConferenceDto> findAll();
    void deleteById(Long id);
    ConferenceDto saveConference(ConferenceDto dto);
}
