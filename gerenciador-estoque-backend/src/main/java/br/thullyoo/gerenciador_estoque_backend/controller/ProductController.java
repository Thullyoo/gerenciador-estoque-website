package br.thullyoo.gerenciador_estoque_backend.controller;

import br.thullyoo.gerenciador_estoque_backend.dto.request.ProductRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.ProductResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/myproducts")
    public ResponseEntity<List<ProductResponse>> listProductsByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.listProductsByUser());
    }
}
