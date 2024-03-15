package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.UserCreationDTO;
import com.trevortran.expensetracker.model.UserSignInDTO;
import com.trevortran.expensetracker.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequestMapping("/user")
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

    @GetMapping("/signup")
    public ModelAndView signUp() {

         ModelAndView modelAndView = new ModelAndView("signup");
         modelAndView.addObject("userSignUpDTO", new UserCreationDTO());
         return modelAndView;
    }
    @PostMapping("/signup")
    public RedirectView signUp(@ModelAttribute("userSignUpDTO") @Valid UserCreationDTO userCreationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        userService.create(userCreationDTO);
//        userService.loadUserByUsername(userCreationDTO.getEmail());
        return new RedirectView("/transaction");
    }

    @GetMapping("/signin")
    public ModelAndView signIn() {
       return new ModelAndView("signin");
    }
}
