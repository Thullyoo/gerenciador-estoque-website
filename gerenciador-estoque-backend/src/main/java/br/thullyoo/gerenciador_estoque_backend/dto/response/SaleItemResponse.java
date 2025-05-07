package br.thullyoo.gerenciador_estoque_backend.dto.response;

public record SaleItemResponse(String name, long code, double unitPrice, int quantity) {
}
