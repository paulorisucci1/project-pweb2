package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.Institution;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
import br.edu.ifpb.pweb2.pweb2.service.AcademicTermService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.FORM;
import static br.edu.ifpb.pweb2.pweb2.config.Paths.INSTITUTIONS;

@AllArgsConstructor
@RequestMapping(INSTITUTIONS)
@Controller
public class InstitutionController {

    private InstitutionService institutionService;

    private AcademicTermService academicTermService;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, Institution institution) {
        modelAndView.setViewName("institutions/form");
        modelAndView.addObject("institution", institution);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createInstitution(@Valid Institution institution, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/form");
            return modelAndView;
        }

        final var createdInstitution = institutionService.create(institution);

        modelAndView.addObject("institution", createdInstitution);
        modelAndView.setViewName("redirect:institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully registered institution.");

        return modelAndView;
    }

    @PutMapping("/{idInstitution}")
    public ModelAndView updateInstitution(@PathVariable Integer idInstitution, @Valid Institution institution, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/form");
            return modelAndView;
        }

        institution.setIdInstitution(idInstitution);
        final var updatedInstitution = institutionService.update(institution);
        modelAndView.addObject("institution", updatedInstitution);
        modelAndView.setViewName("redirect:/institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully updated institution.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/list");

        final var institutionsList = institutionService.listInstitutions();
        modelAndView.addObject("institutionsList", institutionsList);

        return modelAndView;
    }

    @GetMapping("/{idInstitution}")
    public ModelAndView getById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/form");

        final var foundInstitution = institutionService.searchById(idInstitution);
        modelAndView.addObject("institution", foundInstitution);

        return modelAndView;
    }

    @DeleteMapping("/{idInstitution}")
    public ModelAndView deleteById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/institutions");

        institutionService.delete(idInstitution);

        return modelAndView;
    }
}
