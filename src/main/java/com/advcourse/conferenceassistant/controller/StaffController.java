package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.StaffService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.impl.FileServiceImpl;
import com.advcourse.conferenceassistant.service.validator.DateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/staff")
@Controller
public class StaffController {

    @Autowired
    private StaffService service;

    @Autowired
    private ConferenceServiceImpl coservice;

    @Autowired
    TopicService topicService;

    @Autowired
    FileServiceImpl fileServiceImpl;


    @Autowired
    DateValidator dateValidator;

    @GetMapping("/registration")
    public String stafflogin(Model model) {
        Staff staff = new Staff();
        model.addAttribute(staff);
        return "staffreg";
    }

    @PostMapping("/registration")
    public String registerStaffAccount(
            @ModelAttribute("staff") @Valid StaffDto accountStaff,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "staffreg";
        }
        service.registerNewStaffDtoAccount(accountStaff);
        return "redirect:/staff/dashboard";


    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/stafflogin";
    }


    @GetMapping("/logout_admin")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/staff/login?logout";
    }

    @GetMapping("/delete-conference/{confId}")
    public String deleteConf(@PathVariable Long confId) {
        coservice.deleteById(confId);
        return "redirect:/staff/dashboard";
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
        if (isStaffHasntConfId(confId, auth)) return "redirect:/forbidden";
        model.addAttribute("Confid", coservice.findById(confId));
        model.addAttribute("allTopic", topicService.findByConfId(confId));
        File uploadDir = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images");

        model.addAttribute("pathImg", uploadDir.getAbsolutePath() + File.separator);
        return "conference-page";
    }

    @GetMapping("/conference-dashboard/{confId}")
    public String confDashPage(@PathVariable Long confId, Model model) {

        return "conference-dashboard";
    }

    @GetMapping("/conference-add")
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
        dateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "conference-add";
        }
        ConferenceDto conferenceDto = coservice.saveConference(dto);
        service.addConference(auth.getName(), conferenceDto);
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/conference-edit/{confId}")
    public String confEditPage(@PathVariable Long confId, Model model, Authentication auth) {

        if (isStaffHasntConfId(confId, auth)) return "redirect:/forbidden";
        model.addAttribute("conference", coservice.findById(confId));
        return "conference-edit";
    }

    private boolean isStaffHasntConfId(@PathVariable Long confId, Authentication auth) {
        StaffDto staff = service.findByEmail(auth.getName());
        return staff.getColabs_id() == null || !staff.getColabs_id().contains(confId);
    }

    @PostMapping("/conference-edit/{confId}")
    public String editConf(@PathVariable Long confId,
                           @ModelAttribute("conference") ConferenceDto dto,
                           BindingResult bindingResult) {

        /**
         * End or start date shouldn't be empty
         * End date should be greater than start date
         * */
        dateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "conference-edit";
        }
        coservice.update(confId, dto);
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/topic-dashboard/{topicfId}")
    public String topicDashPage() {

        return "topic-dashboard";
    }

    @GetMapping("/topic-add/{confId}")
    public String topicAddPage(@PathVariable long confId,
                               Model model) {
        model.addAttribute("topic", new TopicDto());
        return "topic-add";
    }

    @PostMapping("/topic-add/{confId}")
    public String topicAdd(@PathVariable long confId,
                           @ModelAttribute("topic") TopicDto dto,
                           BindingResult bindingResult,
                           @RequestParam("file") MultipartFile file

    ) {
        dateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "topic-add";
        }
        //TODO set upload dir path in properties
        File uploadDir = new File(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "images");
        if (!uploadDir.exists()) {
            log.info("Dir isn't exists : " + uploadDir.getAbsolutePath());
            boolean mkdir = uploadDir.mkdirs();
            log.info("Create dir {}", mkdir);
        }

        dto.setConfId(confId);
        if (!file.isEmpty()) {
            dto.setSpeakerimg(fileServiceImpl.uploadFile(file, uploadDir.getAbsolutePath()));
        }

        topicService.save(dto);
        return "redirect:/staff/conference-page/" + dto.getConfId();

    }

    @GetMapping("/topic-edit/{topicId}")
    public String topicEditPage(@PathVariable Long topicId, Model model, Authentication auth) {
        TopicDto topic = topicService.findById(topicId);
        long confId = topic.getConfId();
        if (isStaffHasntConfId(confId, auth)) return "redirect:/forbidden";
        model.addAttribute("topic", topic);
        return "topic-edit";
    }

    @PostMapping("/topic-edit/{topicId}")
    public String postTopicEditPage(@PathVariable Long topicId,
                                    @ModelAttribute("topic") TopicDto dto,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file){
        dateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "topic-edit";
        }
        TopicDto topicById = topicService.findById(topicId);
        dto.setId(topicId);
        dto.setConfId(topicById.getConfId());
        //TODO set upload dir path in properties
        File uploadDir = new File(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "images");
        if (!uploadDir.exists()) {
            log.info("Dir isn't exists : " + uploadDir.getAbsolutePath());
            boolean mkdir = uploadDir.mkdirs();
            log.info("Create dir {}", mkdir);
        }
        if (!file.isEmpty()) {
            dto.setSpeakerimg(fileServiceImpl.uploadFile(file, uploadDir.getAbsolutePath()));
        }
        topicService.update(topicId,dto);
        return "redirect:/staff/conference-page/" + dto.getConfId();
    }


    @GetMapping("/list")
    public String getStaffList() {
        return "stafflist";
    }


}
