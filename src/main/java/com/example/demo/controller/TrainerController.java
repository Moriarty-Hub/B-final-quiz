package com.example.demo.controller;

import com.example.demo.entity.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/trainers")
    public ResponseEntity<Trainer> addTrainee(@RequestBody @Valid Trainer trainer) {
        return new ResponseEntity<>(trainerService.addTrainer(trainer), HttpStatus.CREATED);
    }
}
