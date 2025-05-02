package br.thullyoo.gerenciador_estoque_backend.controller;

import br.thullyoo.gerenciador_estoque_backend.dto.request.ProductRequest;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/register")
    public ResponseEntity<Product> registerProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.registerProduct(productRequest));
    }
}
