package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Visitor findByEmail(String email);

    Visitor findByName(String name);

    Visitor findByEmailAndVisit(String email, Conference conference);

    List<Visitor> findAll();


}
