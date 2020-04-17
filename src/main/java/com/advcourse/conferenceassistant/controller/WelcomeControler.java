package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Controller
public class WelcomeControler {

    @Autowired
    ConferenceService conferenceService;
    @Autowired
    TopicService topicService;

    @Autowired
    VisitorServiceImpl visitorService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<ConferenceDto> conferences = conferenceService.findAll();
        model.addAttribute("conferences", conferences);
        return "index";
    }
    @GetMapping("/{confId}/schedule")
    public String schPage(@PathVariable Long confId, Model model, @CookieValue(value = "email", defaultValue = "defaultCookieValue")
            String email) {
        log.debug("Adding a conference to model with id: {}", conferenceService.findById(confId).getId());
        model.addAttribute("conference", conferenceService.findById(confId));
        log.debug("Received topics in quantity of: {} ", topicService.findByConfId(confId).size());
        model.addAttribute("topics", topicService.findByConfId(confId));
        model.addAttribute("email", email);
        model.addAttribute("isVisitorHasCurrentConference",visitorService.isVisitorHasConferenceId(email,confId));
        return "schedule";
    }



}
