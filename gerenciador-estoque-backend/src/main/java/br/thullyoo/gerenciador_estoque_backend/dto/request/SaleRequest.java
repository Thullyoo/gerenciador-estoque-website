package br.thullyoo.gerenciador_estoque_backend.dto.request;

import java.util.List;

public record SaleRequest(long productId, int quantity) {
}
