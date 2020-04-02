package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class StaffController {
    @Autowired
    private StaffServiceImpl service;

    @Autowired
    private ConferenceServiceImpl coservice;

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
        service.registerNewStaffDtoAccount(accountStaff);
        return "redirect:/dashboard";


    }

    @GetMapping("/stafflogin")
    public String getLoginPage() {
        return "/stafflogin";
    }


    @GetMapping("/logout_admin")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/stafflogin?logout";
    }

    @GetMapping("/delete-conference/{confId}")
    public String deleteConf(@PathVariable Long confId) {
        coservice.deleteById(confId);
        return "forward:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashPage(Model model) {
        model.addAttribute("Conflist", coservice.findAll());
        return "staffdashboard";
    }


    @GetMapping("/conference-page/{confId}")
    public String confPage(@PathVariable Long confId, Model model) {
        model.addAttribute("Confid", coservice.findById(confId));
        return "conference-page";
    }

    @GetMapping("/conference-dashboard/{confId}")
    public String confDashPage(@PathVariable Long confId, Model model) {

        return "conference-dashboard";
    }

    @GetMapping("/conferenceadd")
    public String addConf() {
        return "conference-add";
    }

    @PostMapping("/conference-add")
    public String addConf(@ModelAttribute("conference") ConferenceDto dto) {
        coservice.saveConference(dto);

        return "redirect:/dashboard";
    }

    @GetMapping("/conference-edit/{confId}")
    public String confEditPage(@PathVariable Long confId, Model model) {
        model.addAttribute("conf", coservice.findById(confId));
        return "conference-edit";
    }

    @PostMapping("/conference-edit/{confId}")
    public String editConf(@PathVariable Long confId, ConferenceDto dto) {
        coservice.update(confId,dto);
        return "redirect:/dashboard";
    }

    @GetMapping("/topic-dashboard/{topicfId}")
    public String topicDashPage() {

        return "topic-dashboard";
    }

    @GetMapping("/topic-add")
    public String topicAddPage() {

        return "topic-add";
    }

    @GetMapping("/topic-edit/{topicfId}")
    public String topicEditPage() {

        return "topic-edit";
    }

}
