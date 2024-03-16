package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.UserCreationDTO;
import com.trevortran.expensetracker.model.UserDTO;
import com.trevortran.expensetracker.security.UserPrincipal;
import com.trevortran.expensetracker.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class UserController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new
                StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView showUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userDTO", new UserDTO(userPrincipal.getFirstName(), userPrincipal.getLastName()));
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("userCreationDTO", new UserCreationDTO());
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signUp(@ModelAttribute("userCreationDTO") @Valid UserCreationDTO userCreationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("signup");
        }
        userService.create(userCreationDTO);
        return new ModelAndView("redirect:/signin");
    }

    @GetMapping("/signin")
    public ModelAndView signIn() {
        return new ModelAndView("signin");
    }

    @GetMapping(value = "/signin", params = {"error"})
    public ModelAndView signInError() {
        ModelAndView modelAndView = new ModelAndView("signin");
        modelAndView.addObject("signInError", true);
        return modelAndView;
    }
}
