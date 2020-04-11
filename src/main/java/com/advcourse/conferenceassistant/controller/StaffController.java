package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.*;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.validator.dateDiffConf.ConferenceValidator;
import com.advcourse.conferenceassistant.service.validator.dateDiffConf.TopicValidator;
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

@RequestMapping("/staff")
@Controller
public class StaffController {

    @Autowired
    private StaffService service;
    @Autowired
    private ConferenceService coservice;
    @Autowired
    private ConferenceValidator conferenceValidator;
    @Autowired
    private TopicService topicService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FileService fileService;
    @Autowired
    TopicValidator topicValidator;

    @GetMapping("/")
    public String staffEnter() {
        return "forward:/dashboard";
    }
    @GetMapping("/registration")
    public String staffLogin(Model model) {
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
        if (!isCurrentConfIdandStaffColabId(confId, auth)) return "redirect:/forbidden";
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
        conferenceValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "conference-add";
        }
        ConferenceDto conferenceDto = coservice.saveConference(dto);
        service.addConference(auth.getName(), conferenceDto);
        return "redirect:/staff/dashboard";
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
        return "redirect:/staff/dashboard";
    }
// TODO: hide this from all users (now it's available without logging in)
    @GetMapping("/topic-dashboard/{topicId}")
    public String topicDashPage(@PathVariable long topicId,
                                Model model) {
        List<QuestionDto> questions = questionService.getQuestionsByTopicIdForStaff(topicId);
        model.addAttribute("questions", questions);
        model.addAttribute("topic", topicService.findById(topicId));
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
                           @RequestParam("file") MultipartFile file, HttpServletRequest request

    ) {
        topicValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/staff/topic-add/"+confId;
        }

        File uploadDir = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        dto.setConfId(confId);

        dto.setSpeakerimg(fileService.uploadFile(file, uploadDir.getAbsolutePath()));

        topicService.save(dto);
        return "redirect:/staff/conference-page/" + dto.getConfId();

    }

    @GetMapping("/topic-edit/{topicfId}")
    public String topicEditPage() {

        return "topic-edit";
    }

    @GetMapping("/list")
    public String getStaffList() {
        return "stafflist";
    }

    private String findDir(File root, String name) {
        if (root.getName().equals(name)) {
            return root.getAbsolutePath();
        }

        File[] files = root.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    String myResult = findDir(f, name);
                    //this just means this branch of the
                    //recursion reached the end of the
                    //directory tree without results, but
                    //we don't want to cut it short here,
                    //we still need to check the other
                    //directories, so continue the for loop
                    if (myResult == null) {
                        continue;
                    }
                    //we found a result so return!
                    else {
                        return myResult;
                    }
                }
            }
        }

        //we don't actually need to change this. It just means we reached
        //the end of the directory tree (there are no more sub-directories
        //in this directory) and didn't find the result
        return null;
    }

}
