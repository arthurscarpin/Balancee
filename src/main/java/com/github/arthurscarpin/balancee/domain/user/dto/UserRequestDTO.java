package com.github.arthurscarpin.balancee.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        Long id,
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
