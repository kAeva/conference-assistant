package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class StaffController {
    @Autowired
    private StaffServiceImpl service;

    @GetMapping("/stafflogin")
    public String stafflogin(Model model) {
        Staff staff = new Staff();
        model.addAttribute(staff);
        return "stafflogin";
    }

    @PostMapping("/registration-staff")
    public String registerStaffAccount(
            @ModelAttribute("staff") StaffDto accountStaff) {
            StaffDto registered = service.registerNewStaffDtoAccount(accountStaff);
        return "redirect:dashboard";


    }
}
