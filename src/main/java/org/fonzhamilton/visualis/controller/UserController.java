package org.fonzhamilton.visualis.controller;

import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.exception.DuplicateUserException;
import org.fonzhamilton.visualis.service.DataServiceImpl;
import org.fonzhamilton.visualis.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private UserServiceImpl userDetailsService;
    @Autowired
    private DataServiceImpl dataService;
    @Autowired
    private DataController dataController;

    @Autowired
    public UserController(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    private String redirectToLogin()
    {
        return "redirect:/login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userDto", new UserDTO());

        return "sign-up";
    }

    @PostMapping("/sign-up-processing")
    public String signupProcess(@Valid @ModelAttribute ("userDto") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            log.debug("Wrong attempt");

            return "sign-up";
        }

        try {
            userDetailsService.create(userDTO);
            return "sign-up-success";
        } catch (DuplicateUserException e) {
            log.debug("Duplicate user or email: {}", e.getMessage());
            // This is the message that thymeleaf uses. It takes the exception and displays it
            model.addAttribute("dupErrorMessage", "Duplicate user or email");
            model.addAttribute("dupEmailMessage", e.getEmailMessage());
            model.addAttribute("dupUsernameMessage", e.getUsernameMessage());
            return "sign-up";
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        log.info("Login page displayed");
        return "login";
    }

    @RequestMapping("/home")
    public String getHome() {
        log.info("home page displayed");
        return "index";
    }

    @RequestMapping("/visualize")
    public String getVisualize(Model model, Authentication authentication) {
        log.info("data page displayed");
        // Fetch names and populate the dropdown
        List<String> nameList = dataController.getNames(authentication);
        model.addAttribute("nameList", nameList);
        return "data";
    }
    
    @GetMapping("/about")
    public String getAboutPage() {
        log.info("about page displayed");
        return "about";
    }
}
