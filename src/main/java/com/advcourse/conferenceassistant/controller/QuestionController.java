package com.advcourse.conferenceassistant.controller;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
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
        QuestionDto newQuestion = questionService.addQuestion(question);
        TopicDto topic = topicService.findById(newQuestion.getTopicId());
        return "redirect:/liveconference/now/" + topic.getConfId();
    }
//TODO:change cookie value name after changing in declaration
    @GetMapping("/like/{questionId}")
    public String likeQuestion(@PathVariable Long questionId,
                               @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                                       String cookieValue) {
        VisitorDto visitorDto = visitorService.findByEmail(cookieValue);
        QuestionDto dto = questionService.like(questionId, visitorDto.getId());
       return "redirect:/liveconference/now/" + topicService.findById(dto.getTopicId()).getConfId();
    }

}
