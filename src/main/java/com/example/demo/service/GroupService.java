package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupInfo;
import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.exception.InsufficientTrainersException;
import com.example.demo.repository.GroupInfoRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GroupService {

    private static final int NUMBER_OF_TRAINERS_OF_EACH_GROUP = 2;
    private static final String SUFFIX_OF_GROUP_NAME = " 组";

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

    public List<Group> autoGrouping() {

        if (trainerRepository.count() < 2) {
            throw new InsufficientTrainersException();
        }

        int numberOfGroup = Long.valueOf(trainerRepository.count() / NUMBER_OF_TRAINERS_OF_EACH_GROUP).intValue();
        for (int i = 1; i < numberOfGroup + 1; i++) {
            groupInfoRepository.save(GroupInfo.builder().id((long) i).name(i + SUFFIX_OF_GROUP_NAME).build());
        }

        Random random = new Random();
        List<Trainee> traineeList = traineeRepository.findAll();
        int numberOfTrainees = Long.valueOf(traineeRepository.count()).intValue();
        for (int i = 0; i < numberOfTrainees; i++) {
            Trainee trainee = traineeList.remove(random.nextInt(traineeList.size()));
            trainee.setGroupId((long) ((i + 1) % numberOfGroup) + 1);
            traineeRepository.save(trainee);
        }

        List<Trainer> trainerList = trainerRepository.findAll();
        int numberOfTrainers = Long.valueOf(trainerRepository.count()).intValue();
        numberOfTrainers = numberOfTrainers % 2 == 0 ? numberOfTrainers : numberOfTrainers - 1;
        for (int i = 0; i < numberOfTrainers; i++) {
            Trainer trainer = trainerList.remove(random.nextInt(trainerList.size()));
            trainer.setGroupId((long) ((i + 1) % numberOfGroup) + 1);
            trainerRepository.save(trainer);
        }

        return getAllGroups();
    }
}
