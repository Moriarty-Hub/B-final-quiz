package com.example.demo.controller;

import com.example.demo.entity.GroupInfo;
import com.example.demo.repository.GroupInfoRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Test
    public void should_return_empty_list_when_not_grouped() throws Exception {
        mockMvc.perform(get("/groups")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void should_return_grouped_list_when_grouped() throws Exception {
        groupInfoRepository.save(GroupInfo.builder().id(1L).name("1 组").build());
        groupInfoRepository.save(GroupInfo.builder().id(2L).name("2 组").build());

        traineeRepository.findById(1L).ifPresent(trainee -> {
            trainee.setGroupId(1L);
            traineeRepository.save(trainee);
        });
        traineeRepository.findById(2L).ifPresent(trainee -> {
            trainee.setGroupId(1L);
            traineeRepository.save(trainee);
        });
        traineeRepository.findById(3L).ifPresent(trainee -> {
            trainee.setGroupId(1L);
            traineeRepository.save(trainee);
        });
        traineeRepository.findById(4L).ifPresent(trainee -> {
            trainee.setGroupId(2L);
            traineeRepository.save(trainee);
        });
        traineeRepository.findById(5L).ifPresent(trainee -> {
            trainee.setGroupId(2L);
            traineeRepository.save(trainee);
        });
        traineeRepository.findById(6L).ifPresent(trainee -> {
            trainee.setGroupId(2L);
            traineeRepository.save(trainee);
        });

        trainerRepository.findById(1L).ifPresent(trainer -> {
            trainer.setGroupId(1L);
            trainerRepository.save(trainer);
        });
        trainerRepository.findById(2L).ifPresent(trainer -> {
            trainer.setGroupId(1L);
            trainerRepository.save(trainer);
        });
        trainerRepository.findById(3L).ifPresent(trainer -> {
            trainer.setGroupId(2L);
            trainerRepository.save(trainer);
        });
        trainerRepository.findById(4L).ifPresent(trainer -> {
            trainer.setGroupId(2L);
            trainerRepository.save(trainer);
        });

        mockMvc.perform(get("/groups")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].groupInfo.id", is(1)))
                .andExpect(jsonPath("$[0].groupInfo.name", is("1 组")))
                .andExpect(jsonPath("$[0].traineeList[0].id", is(1)))
                .andExpect(jsonPath("$[0].traineeList[0].name", is("沈乐棋")))
                .andExpect(jsonPath("$[0].traineeList[0].office", is("Chengdu")))
                .andExpect(jsonPath("$[0].traineeList[0].email", is("leqi.shen@thoughtworks.com")))
                .andExpect(jsonPath("$[0].traineeList[0].github", is("leqi.shen@github.com")))
                .andExpect(jsonPath("$[0].traineeList[0].zoomId", is("leqi.shen@zoom.com")))
                .andExpect(jsonPath("$[0].trainerList[0].id", is(1)))
                .andExpect(jsonPath("$[0].trainerList[0].name", is("张钊")))
                .andExpect(jsonPath("$[1].groupInfo.id", is(2)))
                .andExpect(jsonPath("$[1].groupInfo.name", is("2 组")))
                .andExpect(jsonPath("$[1].traineeList[0].id", is(4)))
                .andExpect(jsonPath("$[1].traineeList[0].name", is("王江林")))
                .andExpect(jsonPath("$[1].traineeList[0].office", is("Chengdu")))
                .andExpect(jsonPath("$[1].traineeList[0].email", is("jianglin.wang@thoughtworks.com")))
                .andExpect(jsonPath("$[1].traineeList[0].github", is("jianglin.wang@github.com")))
                .andExpect(jsonPath("$[1].traineeList[0].zoomId", is("jianglin.wang@zoom.com")))
                .andExpect(jsonPath("$[1].trainerList[0].id", is(3)))
                .andExpect(jsonPath("$[1].trainerList[0].name", is("桂溪京")));
    }

}
