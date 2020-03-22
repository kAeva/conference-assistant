package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
