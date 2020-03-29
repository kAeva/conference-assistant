package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.impl.VisitorServiceImpl;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VisitorController {

    @Autowired
    private VisitorServiceImpl service;


    /**
     * page with form to input visitor email
     *
     */
    @GetMapping("/")
    public String homePage(Model model) {
        Visitor visitor = new Visitor();
        model.addAttribute(visitor);

        return "index";
    }

    /**
     * adding email to cookie
     * adding visitor to bd
     */
    @PostMapping("/registration-visitor")
    public String registerUserAccount(
            @ModelAttribute("visitor") VisitorDto accountVisitor,
            HttpServletRequest request,
            HttpServletResponse response) {

        VisitorDto registered = service.registerNewVisitorDtoAccount(accountVisitor);


        Cookie[] cookies = request.getCookies();
        Cookie newCookie = new Cookie("testCookie", registered.getEmail());

        // set how long cookie is valid in seconds
        newCookie.setMaxAge(24 * 60 * 60);
        // adding cookie
        // adding cookie
        response.addCookie(newCookie);
        return "redirect:liveconference";


    }

    /**
     * delete email from cookie
     * and return to page "/"
     */
    @PostMapping("/logout")
    String signOut(HttpServletRequest request,
                   HttpServletResponse response) {


        Cookie newCookie = new Cookie("testCookie", "no_cookie");
        newCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(newCookie);
        return "redirect:/";
    }
}
