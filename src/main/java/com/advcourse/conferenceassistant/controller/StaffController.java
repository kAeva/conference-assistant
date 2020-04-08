package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.StaffServiceImpl;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.dto.mapper.ConferenceMapper;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.validator.dateDiffConf.ConferenceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class StaffController {
    @Autowired
    private StaffServiceImpl service;

    @Autowired
    private ConferenceServiceImpl coservice;

    @Autowired
    ConferenceValidator conferenceValidator;

    @Autowired
    TopicService topicService;


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
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashPage(Model model, Authentication auth) {
        List<ConferenceDto> dtoList = coservice.findAll();

        Set<Long> colabs_id = service.findByEmail(auth.getName()).getColabs_id();
        List<String> roles = auth.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());


        model.addAttribute("roles", roles);
        model.addAttribute("colabs_id", List.copyOf(colabs_id));
        model.addAttribute("Conflist", dtoList);

        return "staffdashboard";
    }


    @GetMapping("/conference-page/{confId}")
    public String confPage(@PathVariable Long confId, Model model, Authentication auth) {
        if (!isCurrentConfIdandStaffColabId(confId, auth)) return "redirect:/forbidden";
        model.addAttribute("Confid", coservice.findById(confId));
        model.addAttribute("allTopic", topicService.findByConfId(confId));
        return "conference-page";
    }

    @GetMapping("/conference-dashboard/{confId}")
    public String confDashPage(@PathVariable Long confId, Model model) {

        return "conference-dashboard";
    }

    @GetMapping("/conferenceadd")
    public String addConf(Model model) {
        ConferenceDto dto = new ConferenceDto();
        model.addAttribute("conference", dto);
        return "conference-add";
    }

    @PostMapping("/conference-add")
    public String addConf(
            @ModelAttribute("conference") ConferenceDto dto,
            BindingResult bindingResult, Authentication auth) {

        /**
         * End or start date shouldn't be empty
         * End date should be greater than start date
         * */
        conferenceValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "conference-add";
        }
        ConferenceDto conferenceDto = coservice.saveConference(dto);
        service.addConference(auth.getName(), conferenceDto);
        return "redirect:/dashboard";
    }

    @GetMapping("/conference-edit/{confId}")
    public String confEditPage(@PathVariable Long confId, Model model, Authentication auth) {

        if (!isCurrentConfIdandStaffColabId(confId, auth)) return "redirect:/forbidden";
        model.addAttribute("conference", coservice.findById(confId));
        return "conference-edit";
    }

    private boolean isCurrentConfIdandStaffColabId(@PathVariable Long confId, Authentication auth) {
        StaffDto staff = service.findByEmail(auth.getName());

        if (staff.getColabs_id() != null && staff.getColabs_id().contains(confId)) {
            return true;
        }
        return false;
    }

    @PostMapping("/conference-edit/{confId}")
    public String editConf(@PathVariable Long confId,
                           @ModelAttribute("conference") ConferenceDto dto,
                           BindingResult bindingResult) {

        /**
         * End or start date shouldn't be empty
         * End date should be greater than start date
         * */
        conferenceValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "conference-edit";
        }
        coservice.update(confId, dto);
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

    @GetMapping("/stafflist")
    public String getStaffList() {
        return "stafflist";
    }

}
