package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.Institution;
import br.edu.ifpb.pweb2.pweb2.model.Student;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
import br.edu.ifpb.pweb2.pweb2.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.FORM;
import static br.edu.ifpb.pweb2.pweb2.config.Paths.STUDENTS;

@Controller
@RequestMapping(STUDENTS)
@AllArgsConstructor
public class StudentController {

    private StudentService studentService;

    private InstitutionService institutionService;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, Student student) {
        modelAndView.setViewName("students/form");
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createStudent(@Valid Student student, BindingResult validation,
                                      ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/form");
            return modelAndView;
        }

        final var createdStudent = studentService.create(student);

        modelAndView.addObject("student", createdStudent);
        modelAndView.setViewName("redirect:students");
        redirectAttributes.addFlashAttribute("message", "Successfully registered student.");

        return modelAndView;
    }

    @PutMapping("/{idStudent}")
    public ModelAndView updateStudent(@PathVariable Integer idStudent, @Valid Student student, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/form");
            return modelAndView;
        }

        student.setIdStudent(idStudent);
        final var updatedStudent = studentService.update(student);
        modelAndView.addObject("student", updatedStudent);
        modelAndView.setViewName("redirect:/students");
        redirectAttributes.addFlashAttribute("message", "Successfully updated student.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        modelAndView.setViewName("students/list");

        final var studentList = studentService.listStudents();
        modelAndView.addObject("studentList", studentList);

        return modelAndView;
    }

    @GetMapping("/{idStudent}")
    public ModelAndView getById(@PathVariable Integer idStudent, ModelAndView modelAndView) {
        modelAndView.setViewName("students/form");

        final var foundStudent = studentService.searchById(idStudent);
        modelAndView.addObject("student", foundStudent);

        return modelAndView;
    }

    @DeleteMapping("/{idStudent}")
    public ModelAndView deleteById(@PathVariable Integer idStudent, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/students");

        studentService.delete(idStudent);

        return modelAndView;
    }

    @ModelAttribute("institutionList")
    public List<Institution> findAllInstitutions() {
        return institutionService.listInstitutions();
    }
}
