package com.advcourse.conferenceassistant.controller;
import com.advcourse.conferenceassistant.repository.QuestionRepository;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/addquestion")
    public String addQuestion(QuestionDto question){

        questionService.addQuestion(question);
        return "/liveconference/"+ question.getTopicId();
    }


}
