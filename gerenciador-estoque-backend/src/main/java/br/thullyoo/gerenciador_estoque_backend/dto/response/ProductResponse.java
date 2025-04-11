package br.thullyoo.gerenciador_estoque_backend.dto.response;

public record ProductResponse(long id, String name, String color, String mark, long code, double price) {
}
