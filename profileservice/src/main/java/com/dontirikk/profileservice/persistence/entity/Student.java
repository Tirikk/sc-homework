package com.dontirikk.profileservice.persistence.entity;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Student(@Id UUID id, String name, String email) {
}
