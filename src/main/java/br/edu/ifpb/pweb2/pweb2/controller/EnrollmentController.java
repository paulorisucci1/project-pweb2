package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.entity.Enrollment;
import br.edu.ifpb.pweb2.pweb2.service.AcademicTermService;
import br.edu.ifpb.pweb2.pweb2.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.ENROLLMENTS;
import static br.edu.ifpb.pweb2.pweb2.config.Paths.FORM;

@Controller
@RequestMapping(ENROLLMENTS)
@AllArgsConstructor
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    private AcademicTermService academicTermService;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, @PathVariable Integer idStudent, Enrollment enrollment) {
        configureModelAndViewToForm(modelAndView, idStudent, enrollment);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(ModelAndView modelAndView, @PathVariable Integer idStudent, @Valid Enrollment enrollment, BindingResult validation,
                               RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/enrollments/form");
            return modelAndView;
        }

        final var createdEnrollment = enrollmentService.createEnrollmentForStudent(enrollment, idStudent);

        modelAndView.addObject("enrollment", createdEnrollment);
        modelAndView.setViewName("redirect:/students/{idStudent}".replace("{idStudent}", idStudent.toString()));
        redirectAttributes.addFlashAttribute("message", "Successfully registered enrollment.");

        return modelAndView;
    }

    @PutMapping("/{idEnrollment}")
    public ModelAndView update(ModelAndView modelAndView, @PathVariable Integer idStudent, @PathVariable Integer idEnrollment, @Valid Enrollment enrollment,
                               BindingResult validation, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/enrollments/form");
            return modelAndView;
        }

        enrollment.setIdEnrollment(idEnrollment);
        final var updatedEnrollment = enrollmentService.update(enrollment);
        modelAndView.addObject("enrollment", updatedEnrollment);
        modelAndView.setViewName("redirect:/students/{idStudent}/enrollments".replace("{idStudent}", idStudent.toString()));
        redirectAttributes.addFlashAttribute("message", "Successfully updated enrollment.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView, @PathVariable Integer idStudent) {
        modelAndView.setViewName("students/enrollments/list");

        final var enrollmentList = enrollmentService.listEnrollmentsOfStudent(idStudent);
        modelAndView.addObject("enrollmentList", enrollmentList);
        modelAndView.addObject("idStudent", idStudent);

        return modelAndView;
    }

    @GetMapping("/{idEnrollment}")
    public ModelAndView getById(ModelAndView modelAndView, @PathVariable Integer idEnrollment, @PathVariable Integer idStudent) {

        final var foundEnrollment = enrollmentService.searchById(idEnrollment);
        configureModelAndViewToForm(modelAndView, idStudent, foundEnrollment);
        return modelAndView;
    }

    @DeleteMapping("/{idEnrollment}")
    public ModelAndView deleteById(@PathVariable Integer idEnrollment, @PathVariable Integer idStudent, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/students/{idStudent}/enrollments"
                .replace("{idStudent}", idStudent.toString()));

        enrollmentService.delete(idEnrollment);

        return modelAndView;
    }

    @GetMapping("/{idEnrollment}/pdf")
    private ModelAndView showPDF(ModelAndView modelAndView, @PathVariable Integer idStudent, @PathVariable Integer idEnrollment) {
        final var pdf = enrollmentService.findEnrollmentPdfById(idEnrollment);

        modelAndView.addObject("enrollment_pdf", pdf);
        modelAndView.setViewName("students/enrollments/pdf_view");

        return modelAndView;
    }

    private void configureModelAndViewToForm(ModelAndView modelAndView, Integer idStudent, Enrollment enrollment) {

        final var academicTermList = enrollmentService.listAcademicTermsForStudent(idStudent);

        modelAndView.addObject("idStudent", idStudent);
        modelAndView.addObject("enrollment", enrollment);
        modelAndView.addObject("academicTermList", academicTermList);
        modelAndView.setViewName("students/enrollments/form");
    }
}
