package com.advcourse.conferenceassistant.controller;

import com.advcourse.conferenceassistant.model.Role;
import com.advcourse.conferenceassistant.model.Staff;
import com.advcourse.conferenceassistant.service.QuestionService;
import com.advcourse.conferenceassistant.service.StaffService;
import com.advcourse.conferenceassistant.service.TopicService;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;
import com.advcourse.conferenceassistant.service.dto.QuestionDto;
import com.advcourse.conferenceassistant.service.dto.StaffDto;
import com.advcourse.conferenceassistant.service.dto.TopicDto;
import com.advcourse.conferenceassistant.service.impl.ConferenceServiceImpl;
import com.advcourse.conferenceassistant.service.impl.FileServiceImpl;
import com.advcourse.conferenceassistant.service.validator.DateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private ConferenceServiceImpl conferenceService;

    @Autowired
    private TopicService topicService;
    @Autowired
    QuestionService questionService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    @Autowired
    private DateValidator dateValidator;

    @Value("${upload.path}")
    String path;

    @GetMapping("/")
    public String staffEnter() {
        return "forward:/staff/dashboard";
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
        return "stafflogin";
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
    public String deleteConf(@PathVariable Long confId, Authentication auth) {
        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }
        conferenceService.deleteById(confId);
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashPage(Model model, Authentication auth) {
        model.addAttribute("conferences", service.findConferenceByStaffEmail(auth.getName()));
        return "staffdashboard";
    }


    @GetMapping("/conference-page/{confId}")
    public String confPage(@PathVariable Long confId, Model model, Authentication auth) {
        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }
        model.addAttribute("conference", conferenceService.findById(confId));
        model.addAttribute("allTopic", topicService.findByConfId(confId));
        return "conference-page";
    }

    @GetMapping("/conference-dashboard/{confId}")
    public String confDashPage(@PathVariable Long confId, Model model, Authentication auth) {
        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }
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
        ConferenceDto conferenceDto = conferenceService.saveConference(dto);
        service.addConference(auth.getName(), conferenceDto);

        /**
         * add all conferences to staffs, who have role SUPERVISOR
         * */
        List<StaffDto> supervisor = service.findStaffByRoles(Role.SUPERVISOR);
        supervisor.forEach(e -> service.addConference(e.getEmail(), conferenceDto));
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/conference-edit/{confId}")
    public String confEditPage(@PathVariable Long confId, Model model, Authentication auth) {

        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }
        model.addAttribute("conference", conferenceService.findById(confId));
        return "conference-edit";
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
        conferenceService.update(confId, dto);
        return "redirect:/staff/dashboard";
    }

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
                               Model model, Authentication auth) {
        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }

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
        dto.setConfId(confId);

        if (!file.isEmpty()) {
            dto.setSpeakerimg(fileServiceImpl.uploadFile(file, path));
        }

        topicService.save(dto);
        return "redirect:/staff/conference-page/" + dto.getConfId();

    }

    @GetMapping("/topic-edit/{topicId}")
    public String topicEditPage(@PathVariable Long topicId, Model model, Authentication auth) {
        TopicDto topic = topicService.findById(topicId);
        if (isStaffHasntConfId(topic.getConfId(), auth)) {
            return "redirect:/forbidden";
        }
        model.addAttribute("topic", topic);
        return "topic-edit";
    }

    @PostMapping("/topic-edit/{topicId}")
    public String postTopicEditPage(@PathVariable Long topicId,
                                    @ModelAttribute("topic") TopicDto dto,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file) {
        dateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "topic-edit";
        }
        TopicDto topicById = topicService.findById(topicId);
        dto.setId(topicId);
        dto.setConfId(topicById.getConfId());

        if (!file.isEmpty()) {
            dto.setSpeakerimg(fileServiceImpl.uploadFile(file, path));
        }
        topicService.update(topicId, dto);
        return "redirect:/staff/conference-page/" + dto.getConfId();
    }

    @GetMapping("/topic-delete/{topicId}")
    public String deleteTopic(@PathVariable Long topicId, Authentication auth) {
        long confId = topicService.findById(topicId).getConfId();
        if (isStaffHasntConfId(confId, auth)) {
            return "redirect:/forbidden";
        }
        topicService.deleteById(topicId);
        return "redirect:/staff/conference-page/" + confId;
    }

    @GetMapping("/list")
    public String getStaffList(Model model, Authentication auth) {

        model.addAttribute("staffAuth", service.findByEmail(auth.getName()));
        model.addAttribute("staffs", service.findAll());
        model.addAttribute("confService", conferenceService);
        return "stafflist";
    }

    @GetMapping("/staff-delete/{staffId}")
    public String deleteStaff(@PathVariable long staffId, Authentication auth) {
        Long id = service.findByEmail(auth.getName()).getId();
        if (id.equals(staffId)) {
            return "redirect:/staff/list";
        }
        service.deleteById(staffId);
        return "redirect:/staff/list";
    }

    @GetMapping("add-privileges/{staffId}")
    public String staffPrevileges(@PathVariable long staffId, Model model, Authentication auth) {
        model.addAttribute("staff", service.findById(staffId));
        model.addAttribute("roles", Set.of(Role.values()));
        model.addAttribute("conferences", service.findConferenceByStaffEmail(auth.getName()).stream().map(ConferenceDto::getId).collect(Collectors.toSet()));
        model.addAttribute("confService", conferenceService);
        return "add-privileges";
    }

    @PostMapping("/add-roles/{staffId}")
    public String addRole(@PathVariable Long staffId,
                          @ModelAttribute("staff") StaffDto dto
    ) {
        service.addRoles(staffId, dto.getRoles());
        return "redirect:/staff/add-privileges/" + staffId;
    }

    @PostMapping("/add-conferenceId/{staffId}")
    public String addConferenceId(@PathVariable Long staffId,
                                  @ModelAttribute("staff") StaffDto dto
    ) {
        service.addConferences(staffId, dto.getColabs_id());
        return "redirect:/staff/add-privileges/" + staffId;
    }

    @GetMapping("/topic-start/{topicId}")
    public String changeStartTime(@PathVariable Long topicId) {
        topicService.updateStartTime(topicId);
        return "redirect:/staff/topic-dashboard/" + topicId;
    }

    @GetMapping("/topic-end/{topicId}")
    public String changeEndTime(@PathVariable Long topicId) {
        topicService.updateEndTime(topicId);
        return "redirect:/staff/topic-dashboard/" + topicId;
    }

    /**
     * return true if current staff hasn't rights to this conference
     */
    private boolean isStaffHasntConfId(@PathVariable Long confId, Authentication auth) {
        StaffDto staff = service.findByEmail(auth.getName());
        return staff.getColabs_id() == null || !staff.getColabs_id().contains(confId);
    }

}
