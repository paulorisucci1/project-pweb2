package br.edu.ifpb.pweb2.pweb2.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static br.edu.ifpb.pweb2.pweb2.paths.Paths.HOME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@ExtendWith(SpringExtension.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetHomePageSuccessfully() throws Exception {
        mockMvc.perform(get(HOME))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
