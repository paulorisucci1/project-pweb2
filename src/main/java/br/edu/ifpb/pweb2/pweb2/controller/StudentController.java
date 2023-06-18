package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.dto.InstitutionDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.Institution;
import br.edu.ifpb.pweb2.pweb2.model.dto.StudentDTO;
import br.edu.ifpb.pweb2.pweb2.model.mapper.InstitutionMapper;
import br.edu.ifpb.pweb2.pweb2.model.mapper.StudentMapper;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
import br.edu.ifpb.pweb2.pweb2.service.StudentService;
import jakarta.transaction.Transactional;
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
@Transactional
public class StudentController {

    private StudentService studentService;

    private InstitutionService institutionService;

    private StudentMapper studentMapper;

    private InstitutionMapper institutionMapper;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, StudentDTO studentDTO) {
        modelAndView.setViewName("students/form");
        modelAndView.addObject("student", studentDTO);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createStudent(@Valid StudentDTO studentDTO, BindingResult validation,
                                      ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/form");
            return modelAndView;
        }

        final var student = studentMapper.toEntity(studentDTO);

        final var createdStudent = studentService.create(student);

        modelAndView.addObject("student", createdStudent);
        modelAndView.setViewName("redirect:students");
        redirectAttributes.addFlashAttribute("message", "Successfully registered student.");

        return modelAndView;
    }

    @PutMapping("/{idStudent}")
    public ModelAndView updateStudent(@PathVariable Integer idStudent, @Valid StudentDTO studentDTO, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("students/form");
            return modelAndView;
        }

        final var student = studentMapper.toEntity(studentDTO);

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
        final var studentListDTO = studentMapper.toDTOList(studentList);
        modelAndView.addObject("studentList", studentList);

        return modelAndView;
    }

    @GetMapping("/{idStudent}")
    public ModelAndView getById(@PathVariable Integer idStudent, ModelAndView modelAndView) {
        modelAndView.setViewName("students/form");

        final var foundStudent = studentService.searchById(idStudent);
        final var studentDTO = studentMapper.toDTO(foundStudent);
        modelAndView.addObject("student", studentDTO);

        return modelAndView;
    }

    @DeleteMapping("/{idStudent}")
    public ModelAndView deleteById(@PathVariable Integer idStudent, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/students");

        studentService.delete(idStudent);

        return modelAndView;
    }

    @ModelAttribute("institutionList")
    public List<InstitutionDTO> findAllInstitutions() {
        final var institutionList = institutionService.listInstitutions();

        final var institutionListDTO = institutionMapper.toDTOList(institutionList);

        return institutionListDTO;
    }
}
