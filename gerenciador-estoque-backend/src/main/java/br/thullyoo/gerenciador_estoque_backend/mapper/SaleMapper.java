package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.dto.request.SaleRequest;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;
import org.springframework.security.core.parameters.P;

public class SaleMapper {

    public static SaleItem toSaleItem(int quantity, double unitPrice, Product product){
        SaleItem saleItem = new SaleItem();
        saleItem.setQuantity(quantity);
        saleItem.setUnitPrice(unitPrice);
        saleItem.setProduct(product);
        return saleItem;
    }

}
