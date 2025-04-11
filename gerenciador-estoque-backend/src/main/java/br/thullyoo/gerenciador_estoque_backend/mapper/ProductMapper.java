package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.dto.request.ProductRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.ProductResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;

public class ProductMapper {

    public static Product toProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setCode(product.getCode());
        product.setColor(product.getColor());
        product.setMark(product.getMark());
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        return product;
    }

    public static ProductResponse toProductResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getColor(), product.getMark(), product.getCode(), product.getPrice());
    }
}
