package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findByEmail(String email);
}
