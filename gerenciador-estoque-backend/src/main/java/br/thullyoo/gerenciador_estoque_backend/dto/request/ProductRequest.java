package br.thullyoo.gerenciador_estoque_backend.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        @NotBlank(message = "Name is required and cannot be blank")
        String name,

        String color,

        String mark,

        Long code,

        @DecimalMin(value = "0.01", message = "Price must be greater than zero")
        double price
) {
}
