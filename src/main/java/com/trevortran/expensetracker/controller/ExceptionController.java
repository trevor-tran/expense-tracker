package com.trevortran.expensetracker.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * A global exception handler controller
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * handle 404 page not found exception
     * @return error page
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public String handlePageNotFoundException() {
        System.out.println("page not found");
        return "error";
    }
}
