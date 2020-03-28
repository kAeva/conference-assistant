package com.advcourse.conferenceassistant.service.validator;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.repository.StaffRepository;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffIsActive {

    @Autowired
    private StaffRepository staffRepository;

    /**
     * if staff exists in db return StafDto
     * if not exists return null
     * */
    public StaffDto getActiveUser(String username) {
        StaffDto user;
        Staff byEmail = staffRepository.findByEmail(username);
        if (byEmail == null) {
            return null;
        }
        user = StaffMapper.toDto(staffRepository.findByEmail(username));
        return user;
    }
}
