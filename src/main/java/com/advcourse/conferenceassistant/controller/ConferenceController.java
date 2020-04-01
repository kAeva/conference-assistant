package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ConferenceController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping("/liveconference")
    public void getError(HttpServletResponse response) throws IOException {
        sendError(response);
    }

    @GetMapping("/liveconference/{confId}")
    public String getLive(
            @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                    String cookieValue,
            @PathVariable Long confId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        // rerurn 404 when conference doesn't exists
        Optional<Conference> byId = conferenceRepository.findById(confId);
        if (byId.isEmpty()) {
            sendError(response);
            return "topicquestions";
        }

        // return localhost080:/confId when visitor haven't registration for this conference yet
        Visitor byEmailAndVisit = visitorRepository.findByEmailAndVisit(cookieValue, byId.get());
        if (byEmailAndVisit == null) {
            return "forward:/"+confId;

        }
        model.addAttribute("conferenceId", confId);
        model.addAttribute("cookieValue", cookieValue);
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

        return "forward:/" + confId;
    }

    public void sendError(HttpServletResponse response) throws IOException {
        response.sendError(404, "Conference not found");
    }

}

