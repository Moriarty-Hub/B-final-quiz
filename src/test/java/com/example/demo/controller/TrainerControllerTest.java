package com.example.demo.controller;

import com.example.demo.entity.Trainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_all_ungrouped_trainers() throws Exception {
        mockMvc.perform(get("/trainers?grouped=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("张钊")))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].name", is("张巍")));
    }

    @Test
    public void should_add_trainer_when_all_params_are_valid() throws Exception {
        Trainer trainer = Trainer.builder().name("New Trainer").build();
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(trainer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void should_throw_exception_when_name_is_null() throws Exception {
        Trainer trainer = Trainer.builder().build();
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(trainer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("The name cannot be null")));
    }
}
