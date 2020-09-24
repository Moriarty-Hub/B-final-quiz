package com.example.demo.controller;

import com.example.demo.entity.Trainee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
public class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_all_ungrouped_trainees() throws Exception {
        mockMvc.perform(get("/trainees?grouped=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("沈乐棋")))
                .andExpect(jsonPath("$[0].office", is("Chengdu")))
                .andExpect(jsonPath("$[0].email", is("leqi.shen@thoughtworks.com")))
                .andExpect(jsonPath("$[0].github", is("leqi.shen@github.com")))
                .andExpect(jsonPath("$[0].zoomId", is("leqi.shen@zoom.com")))
                .andExpect(jsonPath("$[9].id", is(10)))
                .andExpect(jsonPath("$[9].name", is("盖迈达")))
                .andExpect(jsonPath("$[9].office", is("Chengdu")))
                .andExpect(jsonPath("$[9].email", is("maida.ge@thoughtworks.com")))
                .andExpect(jsonPath("$[9].github", is("maida.ge@github.com")))
                .andExpect(jsonPath("$[9].zoomId", is("maida.ge@zoom.com")));
    }

    @Test
    public void should_add_trainee_when_all_params_are_valid() throws Exception {
        Trainee trainee = Trainee.builder().name("董翔锐").office("Chengdu").email("xiangrui.dong@thoughtworks.com")
                .github("xiangrui.dong@github.com").zoomId("xiangrui.dong@zoom.com").build();
        mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(trainee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Nested
    class ShouldThrowException {

        Trainee invalidTrainee;

        @BeforeEach
        public void setUp() {
            invalidTrainee = Trainee.builder().name("Anonymous").office("Chengdu").email("anonymous@thoughtworks.com")
                    .github("anonymous@github.com").zoomId("anonymous@zoom.com").build();
        }

        @Test
        public void when_name_is_null() throws Exception {
            invalidTrainee.setName(null);
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The name cannot be null")));
        }

        @Test
        public void when_office_is_null() throws Exception {
            invalidTrainee.setOffice(null);
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The office cannot be null")));
        }

        @Test
        public void when_email_is_null() throws Exception {
            invalidTrainee.setEmail(null);
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The email address cannot be null")));
        }

        @Test
        public void when_email_format_is_invalid() throws Exception {
            invalidTrainee.setEmail("null");
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The format of email address is not valid")));
        }

        @Test
        public void when_github_is_null() throws Exception {
            invalidTrainee.setGithub(null);
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The github address cannot be null")));
        }

        @Test
        public void when_zoom_id_is_null() throws Exception {
            invalidTrainee.setZoomId(null);
            mockMvc.perform(post("/trainees")
                    .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invalidTrainee)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                    .andExpect(jsonPath("$.message", is("The zoom id cannot be null")));
        }

    }
}
