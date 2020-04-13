package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Slf4j
@Controller
public class TempControler {

    @Autowired
    ConferenceService conferenceService;
    @Autowired
    TopicService topicService;

    @GetMapping("/{confId}/schedule")
    public String schPage(@PathVariable Long confId, Model model) {
        log.debug("Adding a conference to model with id: " + conferenceService.findById(confId).getId());
        model.addAttribute("conference", conferenceService.findById(confId));
        log.debug("Received topics in quantity of: " + topicService.findByConfId(confId).size());
        model.addAttribute("topics", topicService.findByConfId(confId));
        return "schedule";
    }



}
