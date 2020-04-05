package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ConferenceController {

    @Autowired
    private VisitorServiceImpl visitorService;

    @Autowired
    private ConferenceServiceImpl conferenceService;

    @GetMapping("/liveconference")
    public void getError() {
        throw new NoSuchConferenceException();
    }

    @GetMapping("/liveconference/{confId}")
    public String getLive(
            @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                    String cookieValue,
            @PathVariable Long confId,
            Model model) {

        model.addAttribute("visitor", visitorService.findByEmailAndVisit(cookieValue, confId));
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

}

