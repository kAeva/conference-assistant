package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
