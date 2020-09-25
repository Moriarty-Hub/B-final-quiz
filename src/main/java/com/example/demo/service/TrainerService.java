package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.repository.TrainerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    private static final String PATH_OF_TRAINEE_DATA_FILE = "src/main/resources/trainer.json";

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
        prepareDataForTrainerRepository();
    }

    private void prepareDataForTrainerRepository() {
        ObjectMapper objectMapper = new ObjectMapper();
        File dataFileOfTrainee = new File(PATH_OF_TRAINEE_DATA_FILE);
        byte[] fileContent = new byte[Long.valueOf(dataFileOfTrainee.length()).intValue()];

        try {
            FileInputStream in = new FileInputStream(dataFileOfTrainee);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<Trainer> traineeList = objectMapper.readValue(new String(fileContent, UTF_8),
                    new TypeReference<List<Trainer>>(){});
            traineeList.forEach(trainerRepository::save);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //TODO GTB：这里只需要查一次库，过滤操作再分条件来。抽取成两个方法。
    public List<Trainer> findAllTrainersByGroupedCondition(Boolean isGrouped) {
        if (!isGrouped) {
            return trainerRepository.findAll().stream()
                    .filter(trainer -> Objects.isNull(trainer.getGroupId()))
                    .collect(Collectors.toList());
        }
        return trainerRepository.findAll().stream()
                .filter(trainer -> !Objects.isNull(trainer.getGroupId()))
                .collect(Collectors.toList());
    }

    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

}
