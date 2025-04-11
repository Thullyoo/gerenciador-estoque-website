package br.thullyoo.gerenciador_estoque_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Email is required and cannot be blank")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message = "Password is required and cannot be blank")
        String password,

        @NotBlank(message = "Name is required and cannot be blank")
        String name
) {
}
