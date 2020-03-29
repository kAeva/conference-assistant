package com.advcourse.conferenceassistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ConferenceController {


    @GetMapping("/liveconference/{confId}")
    public String getLive(
            @CookieValue(value = "testCookie", defaultValue = "defaultCookieValue")
                    String cookieValue,
            @PathVariable Long confId,
            Model model,
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String result;
        //check for available cookie
        if (cookies != null && !"no_cookie".equals(cookies[0].getValue())) {
            result = cookies[0].getValue();
        } else {
            return "redirect:/"+confId;
        }
        model.addAttribute("conferenceId",confId);
        model.addAttribute("cookieValue", result);
        return "topicquestions";
    }


}

