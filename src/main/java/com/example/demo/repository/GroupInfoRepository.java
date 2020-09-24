package com.example.demo.repository;

import com.example.demo.entity.GroupInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupInfoRepository extends CrudRepository<GroupInfo, Long> {

    @Override
    List<GroupInfo> findAll();

}
