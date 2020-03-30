package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.repository.ConferenceRepository;
import com.advcourse.conferenceassistant.service.VisitorServiceImpl;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class VisitorController {

    @Autowired
    private VisitorServiceImpl service;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping("/")
    public void homePage(HttpServletResponse response) throws IOException {
        response.sendError(404, "Conference not found");

    }

    /**
     * page with form to input visitor email
     */
    @GetMapping("/{confId}")
    public String homePage(@PathVariable Long confId,
                           Model model, HttpServletResponse response) throws IOException {

        VisitorDto dto = new VisitorDto();
        Conference conference = new Conference();

        Optional<Conference> byId = conferenceRepository.findById(confId);
        // rerurn 404 when conference doesn't exists
        if (byId.isEmpty()) {
            response.sendError(404, "Conference not found");
        } else {
            conference = byId.get();

        }
        model.addAttribute("conference", conference);
        dto.setConfId(confId);
        model.addAttribute("visitor", dto);

        return "index";
    }

    /**
     * adding email to cookie
     * adding visitor to bd
     */
    @PostMapping("/registration-visitor/{confId}")
    public String registerUserAccount(
            @ModelAttribute("visitor") @Valid VisitorDto accountVisitor,
            BindingResult bindingResult,
            @PathVariable Long confId,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        VisitorDto registered = service.registerNewVisitorDtoAccount(accountVisitor);
        Cookie newCookie = new Cookie("testCookie", registered.getEmail());

        //without this method not working cookie!!!
        newCookie.setPath("/");
        // set how long cookie is valid in seconds
        newCookie.setMaxAge(24 * 60 * 60);
        // adding cookie
        response.addCookie(newCookie);

        return "redirect:/liveconference/" + confId;


    }

    /**
     * delete email from cookie
     * and return to page "/"
     */
    @PostMapping("/logout/{confId}")
    String signOut(@PathVariable Long confId,
                   HttpServletResponse response) {


        Cookie newCookie = new Cookie("testCookie", "no_cookie");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        return "redirect:/" + confId;
    }
}
