package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
}
