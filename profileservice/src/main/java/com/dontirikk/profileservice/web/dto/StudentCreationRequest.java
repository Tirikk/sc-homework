package com.dontirikk.profileservice.web.dto;

import jakarta.validation.constraints.Email;

public record StudentCreationRequest(String name, @Email String email) {
}
