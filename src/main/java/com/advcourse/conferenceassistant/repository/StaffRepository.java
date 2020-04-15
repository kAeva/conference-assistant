package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);

    Staff findByEmailAndPass(String email, String pass);

    List<Staff> findStaffByRoles(Role role);
}
