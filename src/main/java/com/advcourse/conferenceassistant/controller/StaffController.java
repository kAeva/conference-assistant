package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class StaffController {
    @Autowired
    private StaffServiceImpl service;

    @GetMapping("/staffreg")
    public String stafflogin(Model model) {
        Staff staff = new Staff();
        model.addAttribute(staff);
        return "staffreg";
    }

    @PostMapping("/registration-staff")
    public String registerStaffAccount(
            @ModelAttribute("staff") @Valid StaffDto accountStaff,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "staffreg";
        }
        StaffDto registered = service.registerNewStaffDtoAccount(accountStaff);
        return "redirect:login-staff";


    }
   /* @PostMapping("/login-staff")
    public String registerStaffAccount(
            @ModelAttribute("staff") StaffDto accountStaff) {
        StaffDto logedin = service.loginStaffDtoAccount(accountStaff);
        return "redirect:dashboard";
    }*/

}
