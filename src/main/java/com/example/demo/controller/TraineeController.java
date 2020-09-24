package com.example.demo.controller;

import com.example.demo.entity.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainees")
    public ResponseEntity<List<Trainee>> findAllTraineesByGroupedCondition(@RequestParam(name = "grouped") Boolean isGrouped) {
        return ResponseEntity.ok(traineeService.findAllTraineesByGroupedCondition(isGrouped));
    }
}
