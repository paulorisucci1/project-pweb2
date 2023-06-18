package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.dto.InstitutionDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.Institution;
import br.edu.ifpb.pweb2.pweb2.model.mapper.InstitutionMapper;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
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

    private InstitutionMapper mapper;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, InstitutionDTO institutionDTO) {
        modelAndView.setViewName("institutions/form");
        modelAndView.addObject("institution", institutionDTO);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createInstitution(@Valid InstitutionDTO institutionDTO, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/form");
            return modelAndView;
        }

        final var institution = mapper.toEntity(institutionDTO);

        institutionService.create(institution);

        modelAndView.setViewName("redirect:institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully registered institution.");

        return modelAndView;
    }

    @PutMapping("/{idInstitution}")
    public ModelAndView updateInstitution(@PathVariable Integer idInstitution, @Valid InstitutionDTO institutionDTO, BindingResult validation,
                                          ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/form");
            return modelAndView;
        }

        final var institution = mapper.toEntity(institutionDTO);

        institution.setIdInstitution(idInstitution);
        institutionService.update(institution);

        modelAndView.setViewName("redirect:/institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully updated institution.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/list");

        final var institutionsList = institutionService.listInstitutions();
        final var institutionListDTO = mapper.toDTOList(institutionsList);
        modelAndView.addObject("institutionsList", institutionListDTO);

        return modelAndView;
    }

    @GetMapping("/{idInstitution}")
    public ModelAndView getById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/form");

        final var foundInstitution = institutionService.searchById(idInstitution);
        final var institutionDTO = mapper.toDTO(foundInstitution);
        modelAndView.addObject("institution", institutionDTO);

        return modelAndView;
    }

    @DeleteMapping("/{idInstitution}")
    public ModelAndView deleteById(@PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/institutions");

        institutionService.delete(idInstitution);

        return modelAndView;
    }
}
