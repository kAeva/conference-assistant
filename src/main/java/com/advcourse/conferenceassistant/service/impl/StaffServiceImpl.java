package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.NoSuchVisitorException;
import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.repository.StaffRepository;
import com.advcourse.conferenceassistant.service.StaffService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.mapper.StaffMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ConferenceServiceImpl conferenceService;

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

    @Transactional
    @Override
    public boolean isActiveUser(String email) {
        Staff byEmail = staffRepository.findByEmail(email);
        return byEmail != null;
    }

    @Override
    public List<StaffDto> findAll() {
        return StaffMapper.toDto(staffRepository.findAll());
    }

    @Override
    public void deleteById(long id) {
        try {
            staffRepository.deleteById(id);
            log.info("Delete staff with id= {}", id);
        } catch (EmptyResultDataAccessException e) {
            log.info("Staff with id={} doesn't exist", id);
            // todo trow exception
        }

    }

    @Override
    public void addRoles(long staffId, Set<Role> roles) {
        StaffDto staff = findById(staffId);
        staff.setRoles(roles);
        update(staffId, staff);
    }

    @Override
    public void addConferences(long staffId, Set<Long> setConfId) {
        StaffDto staff = findById(staffId);
        staff.setColabs_id(setConfId);
        update(staffId, staff);
    }

    @Override
    public List<ConferenceDto> findConferenceByStaffEmail(String email) {
        StaffDto staff = findByEmail(email);
        Set<Long> colabs_id = staff.getColabs_id();
        Set<Long> allIdConferences = conferenceService.findAll().stream().map(ConferenceDto::getId).collect(Collectors.toSet());
        allIdConferences.retainAll(colabs_id);
        return allIdConferences.stream().map(e -> conferenceService.findById(e)).collect(Collectors.toList());

    }


}
