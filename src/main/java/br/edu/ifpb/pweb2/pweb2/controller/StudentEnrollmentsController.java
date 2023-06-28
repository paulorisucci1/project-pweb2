package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.Enrollment;
import br.edu.ifpb.pweb2.pweb2.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.STUDENT_ENROLLMENTS;
import static br.edu.ifpb.pweb2.pweb2.config.Paths.FORM;

@Controller
@RequestMapping(STUDENT_ENROLLMENTS)
@AllArgsConstructor
public class StudentEnrollmentsController {

    private EnrollmentService enrollmentService;

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
        modelAndView.setViewName("redirect:/students");
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
        modelAndView.setViewName("redirect:/students");
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

//    @GetMapping("/expired")
//    public ModelAndView listExpiredEnrollments(ModelAndView modelAndView, @PathVariable Integer idStudent) {
//        modelAndView.setViewName("students/enrollments/list");
//
//        final var expiredEnrollmentList = enrollmentService.listExpiredEnrollments(idStudent);
//        modelAndView.addObject("enrollmentList", expiredEnrollmentList);
//        modelAndView.addObject("idStudent", idStudent);
//
//        return modelAndView;
//    }

//    @GetMapping("/expiring")
//    public ModelAndView listExpiringEnrollments(ModelAndView modelAndView, @PathVariable Integer idStudent, Integer days) {
//        modelAndView.setViewName("students/enrollments/list");
//
//        final var expiredEnrollmentList = enrollmentService.listExpiringEnrollmentsOfStudentInXDays(idStudent, days);
//        modelAndView.addObject("enrollmentList", expiredEnrollmentList);
//        modelAndView.addObject("idStudent", idStudent);
//
//        return modelAndView;
//    }

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

    @GetMapping("/{idEnrollment}/downloadPDF")
    public ResponseEntity<byte[]> downloadEnrollmentPDF(@PathVariable Integer idEnrollment) {
        final var enrollment = enrollmentService.searchById(idEnrollment);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + enrollment.getFileName())
                .body(enrollment.getEnrollmentDocument());
    }

    @PostMapping("/{idEnrollment}/make-current")
    public ModelAndView changeCurrentEnrollment(@PathVariable Integer idStudent, @PathVariable Integer idEnrollment, ModelAndView modelAndView) {
        enrollmentService.changeStudentCurrentEnrollment(idStudent, idEnrollment);
        modelAndView.setViewName("redirect:/students/{idStudent}/enrollments"
                .replace("{idStudent}", idStudent.toString()));
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
