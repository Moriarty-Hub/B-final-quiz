package com.example.demo.controller;

import com.example.demo.entity.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:1234")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<Trainer>> findAllTraineesByGroupedCondition(@RequestParam(name = "grouped") Boolean isGrouped) {
        return ResponseEntity.ok(trainerService.findAllTrainersByGroupedCondition(isGrouped));
    }
}
