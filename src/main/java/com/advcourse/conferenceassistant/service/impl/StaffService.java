package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.repository.StaffRepository;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
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
        Staff staffByEP = staffRepository.findByEmailAndPass(dto.getEmail(),dto.getPass());
        if (staffByEP != null ) {
            return dto;
        }
        if (staff.getRoles()==null){
            staff.setRoles(Set.of(Role.MODERATOR));
        }
        return StaffMapper.toDto(staffRepository.save(staff));

    }




}
