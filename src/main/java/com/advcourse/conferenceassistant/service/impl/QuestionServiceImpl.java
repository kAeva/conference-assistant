package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Question;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.QuestionRepository;
import com.advcourse.conferenceassistant.repository.TopicRepository;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.dto.mapper.QuestionMapper;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    VisitorRepository visitorRepository;
    @Autowired
    VisitorService visitorService;
    @Autowired
    QuestionService questionService;
    @Autowired
    TopicRepository topicRepository;

    @Override
    public QuestionDto getQuestionById(long id, String email) {
        Question q = questionRepository.findById(id).get();
        Long conf_id = q.getTopic().getConference().getId();
        Visitor visitor = VisitorMapper.fromDto(visitorService.findByEmailAndVisit(email, conf_id));

        Set<Visitor> likes = q.getLikes();
        return QuestionMapper.toDto(q, likes.contains(visitor), likes.size());
    }

    @Override
    public List<QuestionDto> getQuestionsByTopicId(long topicId, String email) {
        Long conf_id = topicRepository
                .findById(topicId)
                .get()
                .getConference()
                .getId();
        Visitor visitor = VisitorMapper.fromDto(visitorService.findByEmailAndVisit(email, conf_id));


        return questionRepository
                .findByTopicId(topicId)
                .stream()
                .map((e) -> {
                            Set<Visitor> likes = e.getLikes();
                            return QuestionMapper.toDto(e, e.getLikes().contains(visitor), likes.size());
                        }
                ).collect(Collectors.toList());
    }
    @Override
    public List<QuestionDto> getTopQuestionsByTopicId(long topicId, String email) {
        List<QuestionDto> questions = questionService.getQuestionsByTopicId(topicId, email);
        log.debug("questions: list before sorting " + questions);
        Comparator<QuestionDto> compareByLikes = Comparator.comparing(QuestionDto::getLikesQuantity);
        Collections.sort(questions, compareByLikes.reversed());
        log.debug("questions: list after sorting " + questions);
        return questions.subList(0, 3);
    }


    @Override
    public QuestionDto addQuestion(QuestionDto dto) {
        Question question = QuestionMapper.fromDto(dto);
        Topic topic = topicRepository.findById(dto.getTopicId()).get();
        question.setTopic(topic);
        Visitor creator = visitorRepository.findById(dto.getCreatorId()).get();
        question.setAuthor(creator);
        question.setLikes(new HashSet<>(Arrays.asList(creator)));
        question.setTime(LocalDateTime.now());

        return QuestionMapper.toDto(questionRepository.save(question), true, question.getLikes().size());
    }

    @Override
    public QuestionDto like(long questionId, long guestId) {
        Visitor guest = visitorRepository.findById(guestId).get();
        Question question = questionRepository.findById(questionId).get();
        question.getLikes().add(guest);

        return QuestionMapper.toDto(questionRepository.save(question), true, question.getLikes().size());
    }

}
