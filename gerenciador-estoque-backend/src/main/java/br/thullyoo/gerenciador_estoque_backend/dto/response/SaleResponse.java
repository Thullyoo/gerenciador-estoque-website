package br.thullyoo.gerenciador_estoque_backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(LocalDateTime date, double total, List<SaleItemResponse> saleItemResponses) {
}
