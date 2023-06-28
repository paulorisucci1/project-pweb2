package br.edu.ifpb.pweb2.pweb2.controller;

import br.edu.ifpb.pweb2.pweb2.model.InstitutionFactory;
import br.edu.ifpb.pweb2.pweb2.service.InstitutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static br.edu.ifpb.pweb2.pweb2.paths.Paths.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InstitutionController.class)
public class InstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InstitutionService institutionService;

    @Test
    public void shouldGetFormSuccessfully() throws Exception {
        mockMvc.perform(get(INSTITUTIONS + FORM))
                .andExpect(status().isOk())
                .andExpect(view().name("institutions/form"));
    }

    @Test
    public void shouldGetInstitutionsSuccessfully() throws Exception {
        final var institutionsList = InstitutionFactory.createInstitutionsList();

        when(institutionService.listInstitutions()).thenReturn(institutionsList);

        mockMvc.perform(get(INSTITUTIONS))
                .andExpect(status().isOk())
                .andExpect(model().attribute("institutionsList", institutionsList))
                .andExpect(view().name("institutions/list"));
    }

    @Test
    public void shouldPostInstitutionsSuccessfully() throws Exception {
        final var newInstitution = InstitutionFactory.createInstitutionWithoutViolations();
        final var requestBody = objectMapper.writeValueAsString(newInstitution);

        when(institutionService.create(any())).thenReturn(newInstitution);

        mockMvc.perform(post(INSTITUTIONS)
                        .content(requestBody))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(model().attribute("institution", newInstitution))
                        .andExpect(view().name("redirect:institutions"));
    }

    @Test
    public void shouldPutInstitutionsSuccessfully() throws Exception {
        final var updatedInstitution = InstitutionFactory.createInstitutionWithoutViolations();
        final var requestBody = objectMapper.writeValueAsString(updatedInstitution);

        when(institutionService.update(any())).thenReturn(updatedInstitution);

        mockMvc.perform(put(INSTITUTIONS+"/"+updatedInstitution.getIdInstitution())
                        .content(requestBody))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("institution", updatedInstitution))
                .andExpect(view().name("redirect:institutions"));
    }

    @Test
    public void shouldGetInstitutionByIdSuccessfully() throws Exception {
        final var foundInstitution = InstitutionFactory.createInstitutionWithoutViolations();

        when(institutionService.searchById(any())).thenReturn(foundInstitution);

        mockMvc.perform(get(INSTITUTIONS+"/"+foundInstitution.getIdInstitution()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("institution", foundInstitution))
                .andExpect(view().name("institutions/form"));
    }

    @Test
    public void shouldDeleteInstitutionByIdSuccessfully() throws Exception {
        final var anyId = 1;

        mockMvc.perform(delete(INSTITUTIONS+"/"+anyId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:institutions"));
    }
}
