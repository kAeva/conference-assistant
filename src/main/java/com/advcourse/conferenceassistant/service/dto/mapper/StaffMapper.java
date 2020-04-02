package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.dto.StaffDto;

import java.util.Set;

public class StaffMapper {

    public static StaffDto toDto(Staff entity){
        return new StaffDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPass(),
                entity.getName(),
                entity.getSurname(),
                entity.getColab(),
                entity.getRoles());
    }

    public static Staff fromDto(StaffDto dto){
        Staff staff = new Staff();
        staff.setId(dto.getId());
        String email = dto.getEmail();
        staff.setEmail(email);
        staff.setPass(dto.getPass());
        staff.setName(dto.getName());
        staff.setSurname(dto.getSurname());
        staff.setColab(dto.getColab_id());
        staff.setRoles(dto.getRoles());

        return staff;
    }
}
