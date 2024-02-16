package com.dontirikk.profileservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Student {
    @Id
    private UUID id;
    private String name;
    private String email;
}
