package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.IVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class ConferenceController {
    @Autowired
    private IVisitorService service;

    @GetMapping("/")
    public String homePage(Model model) {
        Visitor visitor = new Visitor();
        model.addAttribute(visitor);

        return "index";
    }


    @GetMapping("/liveconference")
    public String getLive(
            @CookieValue(value = "testCookie",
                    defaultValue = "defaultCookieValue")
                    String cookieValue, Model model, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String result;

        if (cookies!=null  ){
            result=cookies[0].getValue();
        } else{
            System.out.println("no cookies");
            return "redirect:/";
        }
        System.out.println("cookieValue "+result);
        model.addAttribute("cookieValue", result);
        return "topicquestions";
    }

    @PostMapping("/registration-visitor")
    public String registerUserAccount(
            @ModelAttribute("visitor")  Visitor accountVisitor,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        Visitor registered = new Visitor();
        registered = createVisitorAccount(accountVisitor);
        Cookie[] cookies = request.getCookies();

        Cookie newCookie = new Cookie("testCookie", registered.getEmail());
        newCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(newCookie);
        System.out.println(newCookie.getName()+" = "+ newCookie.getValue());
        return "redirect:liveconference";


    }

    private Visitor createVisitorAccount(Visitor accountVisitor) {
        Visitor registered = null;
        registered = service.registerNewVisitorAccount(accountVisitor);
        /*try {
            registered = service.registerNewVisitorAccount(accountVisitor);
        } catch (EmailExistsException e) {
            return null;
        }*/
        return registered;
    }

}

