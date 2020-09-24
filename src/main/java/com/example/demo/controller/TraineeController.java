package com.example.demo.controller;

import com.example.demo.entity.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:1234")
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainees")
    public ResponseEntity<List<Trainee>> findAllTraineesByGroupedCondition(@RequestParam(name = "grouped") Boolean isGrouped) {
        return ResponseEntity.ok(traineeService.findAllTraineesByGroupedCondition(isGrouped));
    }

    @PostMapping("/trainees")
    public ResponseEntity<Trainee> addTrainee(@RequestBody @Valid Trainee trainee) {
        return new ResponseEntity<>(traineeService.addTrainee(trainee), HttpStatus.CREATED);
    }
}
