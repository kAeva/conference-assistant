package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
    Staff findByPass(String pass);
}
