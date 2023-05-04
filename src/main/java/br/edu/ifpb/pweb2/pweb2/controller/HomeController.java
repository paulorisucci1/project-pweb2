package br.edu.ifpb.pweb2.pweb2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.HOME;

@RequestMapping(HOME)
@Controller
public class HomeController {

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
