package com.advcourse.conferenceassistant.controller;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ConferenceService conferenceService;
    @Autowired
    TopicService topicService;

    @PostMapping("/add-question")
    public String addQuestion(QuestionDto question){
//        TODO: refactor this later
        QuestionDto newQuestion = questionService.addQuestion(question);
        TopicDto topic = topicService.findById(newQuestion.getTopicId());
        ConferenceDto conferenceCurrent = conferenceService.findById(topic.getConfId());
        return "redirect:/liveconference/now/" + conferenceCurrent.getId();
    }


}
