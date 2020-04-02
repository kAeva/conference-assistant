package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
//    List<Conference> findByNameIsLike(String name);


    @Override
    List<Conference> findAll();
}
