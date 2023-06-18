package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.dto.AcademicTermDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.mapper.AcademicTermMapper;
import br.edu.ifpb.pweb2.pweb2.service.AcademicTermService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.edu.ifpb.pweb2.pweb2.config.Paths.*;

@AllArgsConstructor
@Controller
@RequestMapping(INSTITUTIONS_ID+ ACADEMIC_TERMS)
public class AcademicTermController {

    private AcademicTermService academicTermService;

    private AcademicTermMapper mapper;

    @GetMapping(FORM)
    public ModelAndView getForm(ModelAndView modelAndView, @PathVariable Integer idInstitution, AcademicTermDTO academicTermDTO) {
        modelAndView.setViewName("institutions/academic_terms/form");
        modelAndView.addObject("idInstitution", idInstitution);
        modelAndView.addObject("academicTerm", academicTermDTO);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@PathVariable Integer idInstitution, @Valid AcademicTermDTO academicTermDTO, BindingResult validation,
                               ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/academic_terms/form");
            return modelAndView;
        }

        final var academicTerm = mapper.toEntity(academicTermDTO);

        academicTermService.createAcademicTermForInstitution(academicTerm, idInstitution);

        modelAndView.setViewName("redirect:/institutions");
        redirectAttributes.addFlashAttribute("message", "Successfully registered academic term.");

        return modelAndView;
    }

    @PutMapping("/{idAcademicTerm}")
    public ModelAndView update(@PathVariable Integer idInstitution, @PathVariable Integer idAcademicTerm, @Valid AcademicTermDTO academicTermDTO,
                               BindingResult validation, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if(validation.hasErrors()) {
            modelAndView.setViewName("institutions/academic_terms/form");
            return modelAndView;
        }

        final var academicTerm = mapper.toEntity(academicTermDTO);

        academicTerm.setIdAcademicTerm(idAcademicTerm);
        academicTermService.update(academicTerm);
        modelAndView.setViewName("redirect:/institutions/{idInstitution}/academic_terms".replace("{idInstitution}", idInstitution.toString()));
        redirectAttributes.addFlashAttribute("message", "Successfully updated academic term.");

        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView, @PathVariable Integer idInstitution) {
        modelAndView.setViewName("institutions/academic_terms/list");

        final var academicTermList = academicTermService.listAcademicTermsOfInstitution(idInstitution);

        final var academicTermDTOList = mapper.toDTOList(academicTermList);

        modelAndView.addObject("academicTermList", academicTermDTOList);
        modelAndView.addObject("idInstitution", idInstitution);

        return modelAndView;
    }

    @GetMapping("/{idAcademicTerm}")
    public ModelAndView getById(@PathVariable Integer idAcademicTerm, @PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("institutions/academic_terms/form");

        final var foundAcademicTerm = academicTermService.searchById(idAcademicTerm);
        final var academicTermDTO = mapper.toDTO(foundAcademicTerm);
        modelAndView.addObject("academicTerm", academicTermDTO);

        return modelAndView;
    }

    @DeleteMapping("/{idAcademicTerm}")
    public ModelAndView deleteById(@PathVariable Integer idAcademicTerm, @PathVariable Integer idInstitution, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/institutions/{idInstitution}/academic_terms"
                .replace("{idInstitution}", idInstitution.toString()));

        academicTermService.delete(idAcademicTerm);

        return modelAndView;
    }
}