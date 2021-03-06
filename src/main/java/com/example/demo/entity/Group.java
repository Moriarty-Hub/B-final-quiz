package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private GroupInfo groupInfo;
    private List<Trainee> traineeList;
    private List<Trainer> trainerList;

}
