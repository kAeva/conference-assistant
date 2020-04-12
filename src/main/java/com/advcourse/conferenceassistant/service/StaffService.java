package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;

import java.util.List;
import java.util.Set;

public interface StaffService {

    StaffDto registerNewStaffDtoAccount(StaffDto accountStaff);

    StaffDto findByEmail(String email);

    StaffDto findById(Long id);

    void addConference(String email, ConferenceDto dto);

    StaffDto update(Long id, StaffDto dto);

    boolean isActiveUser(String email);

    List<StaffDto> findAll();

    void deleteById(long id);

    void addRoles(long staffId,Set<Role> roles);

    void addConferences (long staffId, Set<Long> setConfId);

}
