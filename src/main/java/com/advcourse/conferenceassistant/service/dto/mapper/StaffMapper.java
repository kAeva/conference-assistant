package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StaffMapper {

    public static StaffDto toDto(Staff entity){
        Set<Long> colabsId = entity.getColab().stream().map(Conference::getId).collect(Collectors.toSet());
        return new StaffDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPass(),
                entity.getName(),
                entity.getSurname(),
                colabsId,
                entity.getRoles());
    }

    public static Staff fromDto(StaffDto dto){
        Set<Conference> setConf= new HashSet<>();
        Conference conf = new Conference();

        Set<Long> colabs_id = dto.getColabs_id();
        if(colabs_id!=null){
            for (Long id: colabs_id){
                conf.setId(id);
                setConf.add(conf);
            }
        }


        Staff staff = new Staff();
        staff.setId(dto.getId());
        String email = dto.getEmail();
        staff.setEmail(email);
        staff.setPass(dto.getPass());
        staff.setName(dto.getName());
        staff.setSurname(dto.getSurname());
        staff.setColab(setConf);
        staff.setRoles(dto.getRoles());

        return staff;
    }
}
