package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.Sale;
import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;

public class SaleMapper {

    public static SaleItem toSaleItem(int quantity, double unitPrice, Product product, Sale sale){
        SaleItem saleItem = new SaleItem();
        saleItem.setQuantity(quantity);
        saleItem.setUnitPrice(unitPrice);
        saleItem.setProduct(product);
        saleItem.setSale(sale);
        return saleItem;
    }

}
