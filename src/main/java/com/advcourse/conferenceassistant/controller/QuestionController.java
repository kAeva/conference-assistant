package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/question")

public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ConferenceService conferenceService;
    @Autowired
    TopicService topicService;
    @Autowired
    VisitorService visitorService;

    @PostMapping("/{topicId}/add-question")
    public String addQuestion(@PathVariable Long topicId, QuestionDto question) {
        log.trace("Question for adding: {}", question.getQuestion());

        if (!question.getQuestion().isEmpty()) {
            String visitorEmail = visitorService.findById(question.getCreatorId()).getEmail();
            question.setCreatorName(visitorEmail.substring(0, visitorEmail.indexOf('@')));
            questionService.addQuestion(question);
        }

        return "redirect:/liveconference/now/" + topicService.findById(topicId).getConfId();
    }

    @GetMapping("/like/{questionId}")
    public String likeQuestion(@PathVariable Long questionId,
                               @CookieValue(value = "email", defaultValue = "defaultCookieValue") String email) {
        log.debug("Received questionId: {}", questionId);

        QuestionDto dto = questionService.like(questionId, visitorService.findByEmail(email).getId());
        return "redirect:/liveconference/now/" + topicService.findById(dto.getTopicId()).getConfId();
    }

    @GetMapping("/unlike/{questionId}")
    public String unlikeQuestion(@PathVariable Long questionId,
                                 @CookieValue(value = "email", defaultValue = "defaultCookieValue") String email) {
        VisitorDto visitorDto = visitorService.findByEmail(email);
        QuestionDto questionDto = questionService.unlike(questionId, visitorDto.getId());

        log.debug("Current visitor id: {}", visitorDto.getId());
        log.debug("Liked by current user? - {}", questionDto.getIsLikedByThisVisitor());

        return "redirect:/liveconference/now/" + topicService.findById(questionDto.getTopicId()).getConfId();
    }

    @GetMapping("/answer/{topicId}/{questionId}")
    public String answerQuestion(@PathVariable Long questionId) {
        log.debug("Question to mark answered: {}", questionId);
        questionService.answerThisQuestion(questionId);
        return "redirect:/staff/topic-dashboard/{topicId}";
    }
}
