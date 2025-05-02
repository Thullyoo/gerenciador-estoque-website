package br.thullyoo.gerenciador_estoque_backend.mapper;

import br.thullyoo.gerenciador_estoque_backend.dto.request.ProductRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.ProductResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.User;

public class ProductMapper {

    public static Product toProduct(ProductRequest productRequest, User owner){
        Product product = new Product();
        product.setCode(productRequest.code());
        product.setColor(productRequest.color());
        product.setMark(productRequest.mark());
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setOwner(owner);
        return product;
    }

    public static ProductResponse toProductResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getColor(), product.getMark(), product.getCode(), product.getPrice());
    }
}
