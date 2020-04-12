package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
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
import java.util.Set;

@RequestMapping("/liveconference")
@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ConferenceController {

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public void getError() {
        throw new NoSuchConferenceException();
    }
    /**
     * page with form to input visitor email
     */
    @GetMapping("/{confId}")
    public String homePage(@PathVariable Long confId, Model model) {
        log.info("New visitor in conference " + confId);
        VisitorDto visitorDto = new VisitorDto();
        visitorDto.setConfId(Set.of(confId));
        log.info("Visitor has been attached to conferenceid " + visitorDto.getConfId());
        model.addAttribute("conference", conferenceService.findById(confId));
        model.addAttribute("visitor", visitorDto);

        return "visitor-registration";
    }
// TODO: add checking conference active status
    @GetMapping("/now/{confId}")
    public String getLive(
            @CookieValue(value = "email", defaultValue = "defaultCookieValue")
                    String email, @PathVariable Long confId,
            Model model) {
        log.info("Redirected to conference page with id " + confId);
//        !!important USE spring.jpa.hibernate.ddl-auto=create-drop application property to have active topics in DB from DataBaseInitials
//        TODO: handle null
        TopicDto currentTopic = topicService.findActiveTopicByConfId(confId);
        log.info("Active topic id: " + currentTopic.getId());
        VisitorDto visitorDto = visitorService.findByEmailAndVisit(email, confId);
        List<QuestionDto> questions = questionService.getQuestionsByTopicId(currentTopic.getId(), visitorDto.getEmail());
        log.info("Received list of questions with size: " + questions.size());

        model.addAttribute("visitor", visitorDto);
        model.addAttribute("topic", currentTopic);
        model.addAttribute("questions", questions);
        model.addAttribute("topquestions", questionService.getTopQuestionsByTopicId(questions));
        model.addAttribute("conference", conferenceService.findById(confId));
        return "topicquestions";
    }

    @GetMapping("/now/{confId}/schedule")
    public String showSchedule(@PathVariable Long confId, Model model) {
        log.debug("Adding a conference to model with id: " + conferenceService.findById(confId).getId());
        model.addAttribute("conference", conferenceService.findById(confId));
        log.debug("Received topics in quantity of: " + topicService.findByConfId(confId).size());
        model.addAttribute("topics", topicService.findByConfId(confId));
        return "schedule";
    }

    /**
     * delete email from cookie
     * and return to page "/"
     */
    @GetMapping("/logout/{confId}")
    public String signOut(@PathVariable Long confId,
                   HttpServletResponse response) {


        Cookie newCookie = new Cookie("email", "no_cookie");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        return "forward:/liveconference/" + confId;
    }

}

