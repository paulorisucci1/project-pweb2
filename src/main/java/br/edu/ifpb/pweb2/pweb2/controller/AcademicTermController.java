package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.service.AcademicTermService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.edu.ifpb.pweb2.pweb2.paths.Paths.*;
import static br.edu.ifpb.pweb2.pweb2.ui.NavPageUtils.createNavePageFromPageWithSize;

@AllArgsConstructor
@Controller
@RequestMapping(INSTITUTIONS_ID+ ACADEMIC_TERMS)
public class AcademicTermController {

    private AcademicTermService academicTermService;

    @GetMapping(FORM)
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getForm(ModelAndView modelAndView, @PathVariable Integer idInstitution, AcademicTerm academicTerm) {
        modelAndView.setViewName("institutions/academic_terms/form");
        modelAndView.addObject("idInstitution", idInstitution);
        modelAndView.addObject("academicTerm", academicTerm);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView create(@PathVariable Integer idInstitution, @Valid AcademicTerm academicTerm, BindingResult validation,
                               ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/academic_terms/form");
            return modelAndView;
        }

        final var createdAcademicTerm = academicTermService.createAcademicTermForInstitution(academicTerm, idInstitution);

        modelAndView.addObject("academicTerm", createdAcademicTerm);
        modelAndView.setViewName("redirect:/institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully registered academic term.");

        return modelAndView;
    }

    @PutMapping("/{idAcademicTerm}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView update(@PathVariable Integer idInstitution, @PathVariable Integer idAcademicTerm, @Valid AcademicTerm academicTerm,
                               BindingResult validation, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/academic_terms/form");
            return modelAndView;
        }

        academicTerm.setIdAcademicTerm(idAcademicTerm);
        final var updatedAcademicTerm = academicTermService.update(academicTerm);
        modelAndView.addObject("academicTerm", updatedAcademicTerm);
        modelAndView.setViewName("redirect:/institutions/{idInstitution}/academic_terms".replace("{idInstitution}", idInstitution.toString()));
        redirectAttributes.addFlashAttribute("message", "Successfully updated academic term.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView, @PathVariable Integer idInstitution,
                                @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        final var academicTermPage = academicTermService.listAcademicTermsOfInstitutionPaging(idInstitution, paging);

        modelAndView.addObject("academicTermList", academicTermPage);
        modelAndView.addObject("idInstitution", idInstitution);
        modelAndView.addObject("navPage", createNavePageFromPageWithSize(academicTermPage, size));
        modelAndView.setViewName("institutions/academic_terms/list");

        return modelAndView;
    }

    @GetMapping("/{idAcademicTerm}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getById(@PathVariable Integer idAcademicTerm, @PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/academic_terms/form");

        final var foundAcademicTerm = academicTermService.searchById(idAcademicTerm);
        modelAndView.addObject("academicTerm", foundAcademicTerm);

        return modelAndView;
    }

    @DeleteMapping("/{idAcademicTerm}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteById(@PathVariable Integer idAcademicTerm, @PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/institutions/{idInstitution}/academic_terms"
                .replace("{idInstitution}", idInstitution.toString()));

        academicTermService.delete(idAcademicTerm);

        return modelAndView;
    }
}