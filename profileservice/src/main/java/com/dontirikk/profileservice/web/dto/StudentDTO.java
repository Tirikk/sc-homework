package com.dontirikk.profileservice.web.dto;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record StudentDTO(UUID id, String name, @Email String email) {
}
