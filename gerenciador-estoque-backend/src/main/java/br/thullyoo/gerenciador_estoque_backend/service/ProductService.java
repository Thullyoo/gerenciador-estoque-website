package br.thullyoo.gerenciador_estoque_backend.service;

import br.thullyoo.gerenciador_estoque_backend.dto.request.ProductRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.ProductResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import br.thullyoo.gerenciador_estoque_backend.mapper.ProductMapper;
import br.thullyoo.gerenciador_estoque_backend.repository.ProductRepository;
import br.thullyoo.gerenciador_estoque_backend.repository.UserRepository;
import br.thullyoo.gerenciador_estoque_backend.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public Product registerProduct(ProductRequest productRequest){
        User user = jwtUtils.getUserByToken();
        Product product = ProductMapper.toProduct(productRequest, user);
        return productRepository.save(product);
    }

    public List<ProductResponse> getProductsByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = userRepository.findByEmail(authentication.getName());

        if (user.isEmpty()){
            throw new BadCredentialsException("User not found");
        }

        Optional<List<Product>> products = productRepository.findByOwner(user.get());

        return products.get().stream().map(ProductMapper::toProductResponse).toList();
    }

    public List<ProductResponse> listProductsByUser() {
        User user = jwtUtils.getUserByToken();

        Optional<List<Product>> productsList = productRepository.findByOwner(user);

        if (productsList.isEmpty()){
            throw new RuntimeException("User doesn't have a product");
        }

        List<ProductResponse> productResponses = productsList.get().stream().map(ProductMapper::toProductResponse).toList();

        return productResponses;
    }
}
