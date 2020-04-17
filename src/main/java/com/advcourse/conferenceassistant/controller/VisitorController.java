package com.advcourse.conferenceassistant.controller;


import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ConferenceServiceImpl conferenceService;

    @Autowired
    private TopicService topicService;

    /**
     * adding email to cookie
     * adding visitor to bd
     */
    @PostMapping("/registration-visitor/{confId}")
    public String registerUserAccount(
            @ModelAttribute("visitor") @Valid VisitorDto accountVisitor,

            BindingResult bindingResult,
            @PathVariable Long confId,
            HttpServletResponse response,
            Model model
            ) {
        model.addAttribute("conference",conferenceService.findById(confId));
        if (bindingResult.hasErrors()) {
            return "visitor-registration";
        }

        VisitorDto registered = visitorService.registerNewVisitorDtoAccount(accountVisitor);
        log.info("New user has been registered: {}", registered);
        //TODO:change cookie value name and check for usages
        Cookie newCookie = new Cookie("email", registered.getEmail());
        //without this method not working cookie!!!
        newCookie.setPath("/");
        // set how long cookie is valid in seconds
        newCookie.setMaxAge(24 * 60 * 60);
        // adding cookie
        response.addCookie(newCookie);

        return "redirect:/liveconference/now/" + confId;


    }


}
