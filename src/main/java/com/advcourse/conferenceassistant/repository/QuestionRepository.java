package com.advcourse.conferenceassistant.repository;

import com.advcourse.conferenceassistant.model.Question;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopicId(long topicId);
}
