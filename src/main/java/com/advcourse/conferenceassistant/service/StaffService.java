package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;

public interface StaffService {

    StaffDto registerNewStaffDtoAccount(StaffDto accountStaff);

    StaffDto findByEmail(String email);

    StaffDto findById(Long id);

    void addConference(String email, ConferenceDto dto);

    StaffDto update(Long id, StaffDto dto);

    boolean isActiveUser(String email);

}
