package com.example.demo.service;

import com.example.demo.entity.Trainee;
import com.example.demo.repository.TraineeRepository;
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
public class TraineeService {

    private final TraineeRepository traineeRepository;

    private static final String PATH_OF_TRAINEE_DATA_FILE = "src/main/resources/trainee.json";

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
        prepareDataForTraineeRepository();

    }

    //TODO GTB：读取文件的操作，可以放在utils
    private void prepareDataForTraineeRepository() {
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
            List<Trainee> traineeList = objectMapper.readValue(new String(fileContent, UTF_8),
                    new TypeReference<List<Trainee>>(){});
            traineeList.forEach(traineeRepository::save);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Trainee> findAllTraineesByGroupedCondition(Boolean isGrouped) {
        if (!isGrouped) {
            return traineeRepository.findAll().stream()
                    .filter(trainee -> Objects.isNull(trainee.getGroupId()))
                    .collect(Collectors.toList());
        }
        return traineeRepository.findAll().stream()
                .filter(trainee -> !Objects.isNull(trainee.getGroupId()))
                .collect(Collectors.toList());
    }

    public Trainee addTrainee(Trainee trainee) {
        return traineeRepository.save(trainee);
    }
}
