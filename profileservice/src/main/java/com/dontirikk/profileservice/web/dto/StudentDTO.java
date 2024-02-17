package com.dontirikk.profileservice.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StudentDTO(@NotNull UUID id, @NotBlank String name, @Email String email) {
}
