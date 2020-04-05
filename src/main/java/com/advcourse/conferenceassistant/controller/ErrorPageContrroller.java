package com.advcourse.conferenceassistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageContrroller {

    @GetMapping("/forbidden")
    public String getFrobidenPage(){
        return "/main/resources/templates/forbidden-page.html";
    }
}
