package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.ENROLLMENTS;

@Controller
@RequestMapping(ENROLLMENTS)
@AllArgsConstructor
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        final var enrollmentList = enrollmentService.list();
        modelAndView.addObject("enrollmentList", enrollmentList);
        modelAndView.setViewName("enrollments/list");
        return modelAndView;
    }

    @GetMapping("/expired")
    public ModelAndView listExpiredEnrollments(ModelAndView modelAndView) {

        final var expiredEnrollmentList = enrollmentService.listExpiredEnrollments();
        modelAndView.addObject("enrollmentList", expiredEnrollmentList);
        modelAndView.setViewName("enrollments/list");

        return modelAndView;
    }

    @GetMapping("/expiring")
    public ModelAndView listExpiringEnrollments(ModelAndView modelAndView, Integer days) {
        modelAndView.setViewName("enrollments/list");

        final var expiredEnrollmentList = enrollmentService.listExpiringEnrollmentsInXDays(days);
        modelAndView.addObject("enrollmentList", expiredEnrollmentList);

        return modelAndView;
    }
}
