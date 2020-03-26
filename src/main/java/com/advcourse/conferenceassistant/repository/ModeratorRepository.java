package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
}
