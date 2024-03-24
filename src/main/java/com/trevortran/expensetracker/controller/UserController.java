package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.UserCreationDTO;
import com.trevortran.expensetracker.model.UserProfileDTO;
import com.trevortran.expensetracker.security.UserPrincipal;
import com.trevortran.expensetracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller that handle user related requests
 */
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
    private AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * display user profile
     * @param userPrincipal authenticated user
     * @return user profile page
     */
    @GetMapping("/profile")
    public ModelAndView showUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("Showing user profile, userId: " + userPrincipal.getId());
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userProfileDTO",
                new UserProfileDTO(userPrincipal.getFirstName(), userPrincipal.getLastName(), userPrincipal.getUsername()));
        return modelAndView;
    }

    /**
     * Display user signup form
     * @return display user signup form
     */
    @GetMapping("/signup")
    public ModelAndView signUp() {
        log.info("Showing user signup form");
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("userCreationDTO", new UserCreationDTO());
        return modelAndView;
    }

    /**
     * Use the user creation data to create an account for the user
     * @param userCreationDTO user info for creating an account
     * @param bindingResult validation
     * @return redirect to signin page after an account created
     */
    @PostMapping("/signup")
    public ModelAndView signUp(@ModelAttribute("userCreationDTO") @Valid UserCreationDTO userCreationDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        log.info("Validating user signup info");
        if (bindingResult.hasErrors()) {
            return new ModelAndView("signup");
        }
        try {
            userService.create(userCreationDTO);

        } catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            bindingResult.rejectValue("email", "duplicate_email", "Email belongs to an existing user");
            return new ModelAndView("signup");
        }

        // manually authenticate and persist user session if authenticated
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(userCreationDTO.getEmail(), userCreationDTO.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        if (authenticationResponse.isAuthenticated()) {
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authenticationResponse);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return new ModelAndView("redirect:/transaction");
        }

        return new ModelAndView("redirect:/signin");
    }

    /**
     * show signin page
     * @return signin page
     */
    @GetMapping("/signin")
    public ModelAndView signIn() {
        log.info("Showing user signin form");
        return showSignInPage(false);
    }

    /**
     * show signin page
     * @return signin page with error message
     */
    @GetMapping(value = "/signin", params = {"error"})
    public ModelAndView signInError() {
        log.info("Showing user signin form with error message");
        return showSignInPage(true);
    }

    // show signin page with or without error
    private ModelAndView showSignInPage(boolean error) {
        ModelAndView modelAndView = new ModelAndView("signin");
        modelAndView.addObject("signInError", error);
        return modelAndView;
    }
}
