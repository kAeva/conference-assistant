package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.repository.StaffRepository;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService implements StaffServiceImpl {
    @Autowired
    private StaffRepository staffRepository;


    /**
     *adding staff to db
     * */
    @Override
    public StaffDto registerNewStaffDtoAccount(StaffDto dto) {
        Staff staff = StaffMapper.fromDto(dto);
        Staff staffByEmail = staffRepository.findByEmail(dto.getEmail());
        Staff staffByPass = staffRepository.findByPass(dto.getPass());
        if (staffByEmail != null && staffByPass != null) {
            return dto;
        }

        return StaffMapper.toDto(staffRepository.save(staff));


    }


}
