package com.trevortran.expensetracker.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * A global exception handler controller
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * handle 404 page not found exception
     * @return error page
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public String handlePageNotFoundException() {
        log.warn("User's trying to access non-existing page");
        return "error";
    }
}
