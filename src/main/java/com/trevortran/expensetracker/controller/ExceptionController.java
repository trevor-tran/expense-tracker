package com.trevortran.expensetracker.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NoHandlerFoundException.class})
    public String handlePageNotFoundException() {
        System.out.println("page not found");
        return "error";
    }
}
