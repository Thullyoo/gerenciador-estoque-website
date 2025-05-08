package br.thullyoo.gerenciador_estoque_backend.dto.response;

public record SaleItemResponse(String name, long code, double unitPrice, int quantity) {
    @Override
    public String toString() {
        return
                "Nome: " + name + "\n" +
                "Codígo: " + code + "\n" +
                "Preço da unidade: " + unitPrice + "\n" +
                "Quantidade: " + quantity + "x\n";
    }
}
