package com.eltosheva.sporthouse.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController {

    @GetMapping("/errorr")
    public ModelAndView crash() {
        throw new DBInconsistentException("TEST SSS");
    }

    @ExceptionHandler({DBInconsistentException.class})
    public ModelAndView hasDBInconsistentException(DBInconsistentException ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
}
