package com.example.demo.repository;

import com.example.demo.entity.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    @Override
    List<Trainer> findAll();

    List<Trainer> findTrainersByGroupId(Long groupId);
}
