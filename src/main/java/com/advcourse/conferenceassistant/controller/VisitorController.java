package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.exception.NoSuchConferenceException;
import com.advcourse.conferenceassistant.model.Topic;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.VisitorService;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.model.IModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ConferenceServiceImpl conferenceService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public void homePage() {
       throw new NoSuchConferenceException();

    }

    /**
     * page with form to input visitor email
     */
    @GetMapping("/liveconference/{confId}")
    public String homePage(@PathVariable Long confId, Model model) {

        VisitorDto dto = new VisitorDto();
        dto.setConfId(confId);
        model.addAttribute("conference", conferenceService.findById(confId));
        model.addAttribute("visitor", dto);

        return "index";
    }

    /**
     * adding email to cookie
     * adding visitor to bd
     */
    @PostMapping("/registration-visitor")
    public String registerUserAccount(
            @ModelAttribute("visitor") @Valid VisitorDto accountVisitor,
            BindingResult bindingResult,
//            @PathVariable Long confId,
            HttpServletResponse response, Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        VisitorDto registered = visitorService.registerNewVisitorDtoAccount(accountVisitor);
        Cookie newCookie = new Cookie("testCookie", registered.getEmail());

        //without this method not working cookie!!!
        newCookie.setPath("/");
        // set how long cookie is valid in seconds
        newCookie.setMaxAge(24 * 60 * 60);
        // adding cookie
        response.addCookie(newCookie);
//        model.addAttribute("currentVisitor", registered.getConfId());

        return "redirect:/liveconference/now/" + registered.getConfId();


    }


}
