package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleItemResponse;
import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.Sale;
import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;

import java.time.LocalDateTime;
import java.util.List;

public class SaleMapper {

    public static SaleItem toSaleItem(int quantity, double unitPrice, Product product, Sale sale){
        SaleItem saleItem = new SaleItem();
        saleItem.setQuantity(quantity);
        saleItem.setUnitPrice(unitPrice);
        saleItem.setProduct(product);
        saleItem.setSale(sale);
        return saleItem;
    }

    public static SaleResponse toSaleResponse(Sale sale){
        List<SaleItemResponse> itemResponses = sale.getItems().stream().map(SaleMapper::toSaleItemResponse).toList();
        return new SaleResponse(sale.getDate() , sale.getTotalAmount(), itemResponses);
    }

    public static SaleItemResponse toSaleItemResponse(SaleItem saleItem){
        return new SaleItemResponse(saleItem.getProduct().getName(), saleItem.getProduct().getCode(), saleItem.getUnitPrice(), saleItem.getQuantity());
    }

}
