package com.advcourse.conferenceassistant.controller;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
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

    @PostMapping("/add-question")
    public String addQuestion(QuestionDto question){
//        TODO: refactor this later
        log.debug("Question for adding: "+ question.getQuestion());
        String visitorEmail = visitorService.findById(question.getCreatorId()).getEmail();
        question.setCreatorName(visitorEmail.substring(0,visitorEmail.indexOf('@')));
        QuestionDto newQuestion = questionService.addQuestion(question);
        TopicDto topic = topicService.findById(newQuestion.getTopicId());
        return "redirect:/liveconference/now/" + topic.getConfId();
    }
//TODO:change cookie value name after changing in declaration
    @GetMapping("/like/{questionId}")
    public String likeQuestion(@PathVariable Long questionId,
                               @CookieValue(value = "email", defaultValue = "defaultCookieValue")
                                       String email) {
        VisitorDto visitorDto = visitorService.findByEmail(email);
        QuestionDto dto = questionService.like(questionId, visitorDto.getId());
       return "redirect:/liveconference/now/" + topicService.findById(dto.getTopicId()).getConfId();
    }
    @GetMapping("/unlike/{questionId}")
    public String unlikeQuestion(@PathVariable Long questionId,
                               @CookieValue(value = "email", defaultValue = "defaultCookieValue")
                                       String email) {
        VisitorDto visitorDto = visitorService.findByEmail(email);
        log.debug("Current visitor id " + visitorDto.getId());
        QuestionDto questionDto = questionService.unlike(questionId, visitorDto.getId());
        log.debug("Liked by current user? - " + questionDto.getIsLikedByThisVisitor());
        return "redirect:/liveconference/now/" + topicService.findById(questionDto.getTopicId()).getConfId();
    }
}
