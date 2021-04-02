package com.eltosheva.sporthouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ModelAndView unathorizationHadler (UnauthorizedException exc) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exceptionUnauthorized", exc.getMessage());
        modelAndView.setViewName("error");

        return modelAndView;
    }
}
