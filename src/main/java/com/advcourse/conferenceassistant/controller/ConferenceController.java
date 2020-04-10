package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.impl.QuestionServiceImpl;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RequestMapping("/liveconference")
@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ConferenceController {

    @Autowired
    private VisitorServiceImpl visitorService;
    @Autowired
    private ConferenceServiceImpl conferenceService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private QuestionServiceImpl questionService;

    @GetMapping("/")
    public void getError() {
        throw new NoSuchConferenceException();
    }

    @GetMapping("/now/{confId}")
    public String getLive(
            @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                    String cookieValue, @PathVariable Long confId,
            Model model) {
        log.info("Redirected to conference page with id " + confId);
        TopicDto currentTopic = topicService.findActiveTopicByConfId(confId);
        log.info("Active topic id: " + currentTopic.getId());
//        !!important USE spring.jpa.hibernate.ddl-auto=create-drop property to have active topics in DB from DataBaseInitials
        VisitorDto visitorDto = visitorService.findByEmailAndVisit(cookieValue, confId);
        List<QuestionDto> questions = questionService.getQuestionsByTopicId(currentTopic.getId(), visitorDto.getEmail());
        log.info("Received list of questions with size: " + questions.size());

        model.addAttribute("visitor", visitorDto);
        model.addAttribute("topic", currentTopic);
        model.addAttribute("questions", questions);
        model.addAttribute("topquestions", questionService.getTopQuestionsByTopicId(questions));
        model.addAttribute("conference", conferenceService.findById(confId));
        return "topicquestions";
    }


    /**
     * delete email from cookie
     * and return to page "/"
     */
    @GetMapping("/logout/{confId}")
    String signOut(@PathVariable Long confId,
                   HttpServletResponse response) {


        Cookie newCookie = new Cookie("testCookie", "no_cookie");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        return "forward:/liveconference/" + confId;
    }

    @GetMapping("/now/{confId}/schedule")
    String showSchedule(@PathVariable Long confId) {
//        TopicDto topicDto = topicService.getBy
        return "schedule";
    }

}

