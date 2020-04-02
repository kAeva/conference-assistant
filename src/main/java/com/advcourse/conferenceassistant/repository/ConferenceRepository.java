package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
 //  List<Conference> findByNameIsLike(String name);
    List<Conference> findAll();
    void deleteByid(Long id);


    @Override
    <S extends Conference> S saveAndFlush(S s);
}
