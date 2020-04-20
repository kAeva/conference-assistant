package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.model.Question;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.QuestionRepository;
import com.advcourse.conferenceassistant.repository.TopicRepository;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.mapper.QuestionMapper;
import com.advcourse.conferenceassistant.service.dto.mapper.VisitorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private final static int TOP_QUESTIONS = 3;

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
    public List<QuestionDto> getQuestionsByTopicId(long topicId, String email) {
        log.trace("In getQuestionsByTopicId() method");
        Long confId = topicRepository
                .findById(topicId)
                .get()
                .getConference()
                .getId();
        Visitor visitor = VisitorMapper.fromDto(visitorService.findByEmailAndVisit(email, confId));
        return questionRepository
                .findByTopicId(topicId)
                .stream()
                .map((q) -> {
                            Set<Visitor> likes = q.getLikes();
                            QuestionDto questionDto = QuestionMapper.toDto(q, likes.contains(visitor), likes.size());
                            log.debug("Question to QuestionDto converted : " + questionDto);
                            return questionDto;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getQuestionsByTopicIdForStaff(long topicId) {
        List<QuestionDto> allQuestions = questionRepository
                .findByTopicId(topicId)
                .stream()
                .map((q) -> {
                            Set<Visitor> likes = q.getLikes();
                            QuestionDto questionDto = QuestionMapper.toDto(q, false, likes.size());
                            log.info("Question to QuestionDto converted : " + questionDto);
                            return questionDto;
                        }
                ).collect(Collectors.toList());
        Comparator<QuestionDto> compareByLikes = Comparator.comparing(QuestionDto::getLikesQuantity);
        allQuestions.sort(compareByLikes.reversed());
        return allQuestions;
    }

    @Override
    public List<QuestionDto> getTopQuestionsByTopicId(List<QuestionDto> questions) {
        Comparator<QuestionDto> compareByLikes = Comparator.comparing(QuestionDto::getLikesQuantity);
        questions.sort(compareByLikes.reversed());

        if (questions.size() >= TOP_QUESTIONS) {
            return questions.subList(0, TOP_QUESTIONS);
        }

        return questions;
    }

    @Override
    public void addQuestion(QuestionDto questionDto) {
        Question question = QuestionMapper.fromDto(questionDto);
        Visitor creator = visitorRepository.findById(questionDto.getCreatorId());
        question.setAuthor(creator);
        Set<Visitor> likes = new HashSet<>();
        likes.add(creator);
        question.setLikes(likes);
        question.setTime(LocalDateTime.now());
        question.setAnswerStatus(false);
        questionRepository.save(question);
    }

    @Override
    public QuestionDto like(long questionId, long guestId) {
        Question question = questionRepository.findById(questionId);
        question.getLikes().add(visitorRepository.findById(guestId));
        return QuestionMapper.toDto(questionRepository.save(question), true, question.getLikes().size());
    }

    @Override
    public QuestionDto unlike(long questionId, long guestId) {
        Question question = questionRepository.findById(questionId);
        question.getLikes().remove(visitorRepository.findById(guestId));
        return QuestionMapper.toDto(questionRepository.save(question), true, question.getLikes().size());
    }

    @Override
    public void answerThisQuestion(long questionId) {
        Question question = questionRepository.findById(questionId);
        question.setAnswerStatus(true);
        questionRepository.save(question);
    }
}
