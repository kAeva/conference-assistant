package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.service.ConferenceService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TempControler {

    @Autowired
   private ConferenceService conferenceService;

    @GetMapping("/dashboard")
    public String dashPage() {
        return "staffdashboard";
    }

    @PostMapping("/dashboard")
    public String dashPagep() {
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
    public String confAddPage() {
        return "conference-add";
    }

    @PostMapping("/conference-add")
    public String addConf(@ModelAttribute("conference")ConferenceDto dto) {
        conferenceService.saveConference(dto);

        return "redirect:/dashboard";
    }

    @GetMapping("/topic-add")
    public String topicAddPage() {
        return "topic-add";
    }
    @GetMapping("/conference-edit")
    public String confEditPage() {
        return "conference-edit";
    }
    @GetMapping("/topic-edit")
    public String topicEditPage() {
        return "topic-edit";
    }
    @GetMapping("/conference-dashboard")
    public String confDashPage() {
        return "conference-dashboard";
    }
    @GetMapping("/topic-dashboard")
    public String topicDashPage() {
        return "topic-dashboard";
    }

}
