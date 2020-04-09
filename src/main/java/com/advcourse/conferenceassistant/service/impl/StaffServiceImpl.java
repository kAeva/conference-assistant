package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.NoSuchVisitorException;
import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.repository.StaffRepository;
import com.advcourse.conferenceassistant.service.StaffService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * adding staff to db
     */
    @Override
    public StaffDto registerNewStaffDtoAccount(StaffDto dto) {
        Staff staff = StaffMapper.fromDto(dto);
        Staff staffByEP = staffRepository.findByEmailAndPass(dto.getEmail(), dto.getPass());
        if (staffByEP != null) {
            return dto;
        }
        if (staff.getRoles() == null) {
            staff.setRoles(Set.of(Role.MODERATOR));
        }
        staff.setPass(passwordEncoder.encode(staff.getPass()));
        return StaffMapper.toDto(staffRepository.save(staff));

    }

    @Override
    public StaffDto findByEmail(String email) {
        return StaffMapper.toDto(staffRepository.findByEmail(email));
    }

    @Override
    public StaffDto findById(Long id) {
        Optional<Staff> byId = staffRepository.findById(id);
        if (byId.isEmpty()) {
            //TODO create new exception
            throw new NoSuchVisitorException();
        }
        return StaffMapper.toDto(byId.get());
    }

    @Override
    public void addConference(String email, ConferenceDto conf) {
        StaffDto staffDto = findByEmail(email);
        staffDto.getColabs_id().add(conf.getId());
        update(staffDto.getId(), staffDto);
    }

    @Override
    public StaffDto update(Long id, StaffDto dto) {
        StaffDto staffDto = findById(id);
        staffDto.setEmail(dto.getEmail());
        staffDto.setName(dto.getName());
        //TODO todo something with password or not
        staffDto.setSurname(dto.getSurname());
        staffDto.setColabs_id(dto.getColabs_id());
        staffDto.setRoles(dto.getRoles());

        return StaffMapper.toDto(staffRepository.save(StaffMapper.fromDto(staffDto)));
    }


}
