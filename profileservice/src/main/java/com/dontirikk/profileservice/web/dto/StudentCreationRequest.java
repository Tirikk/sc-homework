package com.dontirikk.profileservice.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StudentCreationRequest(@NotBlank String name, @Email String email) {
}
