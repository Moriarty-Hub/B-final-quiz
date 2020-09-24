package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name cannot be null")
    private String name;

    @NotNull(message = "The office cannot be null")
    private String office;

    @NotNull(message = "The email address cannot be null")
    @Email(message = "The format of email address is not valid")
    private String email;

    @NotNull(message = "The github address cannot be null")
    private String github;

    @NotNull(message = "The zoom id cannot be null")
    private String zoomId;

    @JsonIgnore
    private Long groupId;
}
