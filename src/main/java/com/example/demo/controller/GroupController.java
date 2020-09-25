package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO GTB：缺少修改组名的接口
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    //TODO GTB：如果没有定制化的返回需求，可以省略ResponseEntity
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PostMapping("/groups/auto-grouping")
    public ResponseEntity<List<Group>> autoGrouping() {
        return ResponseEntity.ok(groupService.autoGrouping());
    }
}
