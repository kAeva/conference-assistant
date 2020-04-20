package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.exception.NotActiveConferenceException;
import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
     * page with form to add visitor email
     */
    @GetMapping("/{confId}")
    public String homePage(@PathVariable Long confId, Model model) {

        log.debug("New visitor in conference: {}", confId);
        ConferenceDto conf = conferenceService.findById(confId);
        final NotActiveConferenceException notActiveConferenceException = new NotActiveConferenceException();
        if (!LocalDateTime.now().isAfter(conf.getStart()) & LocalDateTime.now().isBefore(conf.getEnd())) {
            log.error("Conference is out of time", notActiveConferenceException);
            throw new NotActiveConferenceException();
        }
        VisitorDto visitorDto = new VisitorDto();
        visitorDto.setConfId(Set.of(confId));
        log.debug("Visitor has been attached to conferenceid: {}", visitorDto.getConfId());
        model.addAttribute("conference", conferenceService.findById(confId));
        model.addAttribute("visitor", visitorDto);

        return "visitor-registration";
    }
    @GetMapping("/now/{confId}")
    public String getLive(
            @CookieValue(value = "email", defaultValue = "defaultCookieValue")
                    String email, @PathVariable Long confId,
            Model model) {
        log.debug("Redirected to conference page with id: {} ", confId);
        TopicDto currentTopic = topicService.findActiveTopicByConfId(confId);
        if (currentTopic==null){
            return "conference-not-available";
        }
        log.debug("Active topic id: {}", currentTopic.getId());
        VisitorDto visitorDto = visitorService.findByEmailAndVisit(email, confId);
        List<QuestionDto> questions = questionService.getQuestionsByTopicId(currentTopic.getId(), visitorDto.getEmail());
        log.debug("Received list of questions with size: {}", questions.size());
        model.addAttribute("visitor", visitorDto);
        model.addAttribute("topic", currentTopic);
        model.addAttribute("questions", questions);
        model.addAttribute("topquestions", questionService.getTopQuestionsByTopicId(questions));
        model.addAttribute("conference", conferenceService.findById(confId));
        return "topicquestions";
    }

    @GetMapping("/now/{confId}/schedule")
    public String showSchedule(@PathVariable Long confId, Model model,  @CookieValue(value = "email", defaultValue = "defaultCookieValue")
            String email) {
        model.addAttribute("conference", conferenceService.findById(confId));
        log.debug("Received topics in quantity of: {}", topicService.findByConfId(confId).size());
        model.addAttribute("topics", topicService.findByConfId(confId));
        model.addAttribute("currentTime", LocalDateTime.now());
        model.addAttribute("email", email);
        model.addAttribute("isVisitorHasCurrentConference",visitorService.isVisitorHasConferenceId(email,confId));
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

