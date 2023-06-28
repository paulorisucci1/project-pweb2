package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static br.edu.ifpb.pweb2.pweb2.paths.Paths.ENROLLMENTS;
import static br.edu.ifpb.pweb2.pweb2.ui.NavPageUtils.createNavePageFromPageWithSize;

@Controller
@RequestMapping(ENROLLMENTS)
@AllArgsConstructor
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam (defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page - 1, size);

        final var enrollments = enrollmentService.list(paging);

        modelAndView.addObject("enrollmentList", enrollments);
        modelAndView.addObject("navPage", createNavePageFromPageWithSize(enrollments, size));
        modelAndView.setViewName("enrollments/list");
        return modelAndView;
    }

    @GetMapping("/expired")
    public ModelAndView listExpiredEnrollments(ModelAndView modelAndView,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam (defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page - 1, size);

        final var expiredEnrollments = enrollmentService.listExpiredEnrollments(paging);
        modelAndView.addObject("enrollmentList", expiredEnrollments);
        modelAndView.addObject("navPage", createNavePageFromPageWithSize(expiredEnrollments, size));
        modelAndView.setViewName("enrollments/list");

        return modelAndView;
    }

    @GetMapping("/expiring")
    public ModelAndView listExpiringEnrollments(ModelAndView modelAndView, Integer days,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam (defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page - 1, size);

        final var expiringEnrollments = enrollmentService.listExpiringEnrollmentsInXDays(days, paging);
        modelAndView.addObject("enrollmentList", expiringEnrollments);
        modelAndView.addObject("navPage", createNavePageFromPageWithSize(expiringEnrollments, size));
        modelAndView.setViewName("enrollments/list");

        return modelAndView;
    }
}
