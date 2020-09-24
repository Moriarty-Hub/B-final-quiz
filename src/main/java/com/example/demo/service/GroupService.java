package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.repository.GroupInfoRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GroupService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final GroupInfoRepository groupInfoRepository;

    public GroupService(TraineeRepository traineeRepository, TrainerRepository trainerRepository, GroupInfoRepository groupInfoRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.groupInfoRepository = groupInfoRepository;
    }

    public List<Group> getAllGroups() {
        List<Group> groupList = new LinkedList<>();
        groupInfoRepository.findAll().forEach(groupInfo -> {
            Group group = Group.builder()
                    .groupInfo(groupInfo)
                    .traineeList(traineeRepository.findTraineesByGroupId(groupInfo.getId()))
                    .trainerList(trainerRepository.findTrainersByGroupId(groupInfo.getId())).build();
            groupList.add(group);
        });
        return groupList;
    }
}
