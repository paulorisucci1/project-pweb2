package br.edu.ifpb.pweb2.pweb2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }
}
