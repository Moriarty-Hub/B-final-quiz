package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "group_info")
//TODO GTB：一般entity不以Info结尾，一般以XxxGroup形式命名
public class GroupInfo {

    @Id
    private Long id;
    private String name;

}
