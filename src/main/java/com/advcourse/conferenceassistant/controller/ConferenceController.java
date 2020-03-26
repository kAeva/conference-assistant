package com.advcourse.conferenceassistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConferenceController {
    @GetMapping("/")
    public String homePage() {
        return "index";
    }
    @GetMapping("/liveconference")
    public String getLive() {
        return "topicquestions";
    }
}
