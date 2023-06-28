package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.Institution;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
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

import static br.edu.ifpb.pweb2.pweb2.paths.Paths.FORM;
import static br.edu.ifpb.pweb2.pweb2.paths.Paths.INSTITUTIONS;
import static br.edu.ifpb.pweb2.pweb2.ui.NavPageUtils.createNavePageFromPageWithSize;

@AllArgsConstructor
@RequestMapping(INSTITUTIONS)
@Controller
public class InstitutionController {

    private InstitutionService institutionService;

    @GetMapping(FORM)
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getForm(ModelAndView modelAndView, Institution institution) {
        modelAndView.setViewName("institutions/form");
        modelAndView.addObject("institution", institution);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    public ModelAndView listAll(ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page, @RequestParam (defaultValue = "5") int size) {
        modelAndView.setViewName("institutions/list");

        Pageable paging = PageRequest.of(page - 1, size);

        final var institutions = institutionService.listInstitutions(paging);
        modelAndView.addObject("institutionsList", institutions);
        modelAndView.addObject("navPage", createNavePageFromPageWithSize(institutions, size));

        return modelAndView;
    }

    @GetMapping("/{idInstitution}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/form");

        final var foundInstitution = institutionService.searchById(idInstitution);
        modelAndView.addObject("institution", foundInstitution);

        return modelAndView;
    }

    @DeleteMapping("/{idInstitution}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/institutions");

        institutionService.delete(idInstitution);

        return modelAndView;
    }
}
