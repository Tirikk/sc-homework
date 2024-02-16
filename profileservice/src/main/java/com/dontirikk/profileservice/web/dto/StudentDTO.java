package com.dontirikk.profileservice.web.dto;

import java.util.UUID;

public record StudentDTO(UUID id, String name, String email) {
}
