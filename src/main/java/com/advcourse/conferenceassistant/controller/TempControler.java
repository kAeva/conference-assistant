package com.advcourse.conferenceassistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControler {


    @GetMapping("/dashboard")
    public String dashPage() {
        return "staffdashboard";
    }
    @GetMapping("/schedule")
    public String schPage() {
        return "schedule";
    }
    @GetMapping("/conference-page")
    public String confPage() {
        return "conference-page";
    }
    @GetMapping("/conference-add")
    public String confEditPage() {
        return "conference-add";
    }
    @GetMapping("/topic-add")
    public String topicEditPage() {
        return "topic-add";
    }
}
