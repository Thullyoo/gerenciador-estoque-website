package br.thullyoo.gerenciador_estoque_backend.service;

import br.thullyoo.gerenciador_estoque_backend.dto.request.SaleItemRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.Sale;
import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import br.thullyoo.gerenciador_estoque_backend.mapper.SaleMapper;
import br.thullyoo.gerenciador_estoque_backend.repository.ProductRepository;
import br.thullyoo.gerenciador_estoque_backend.repository.SaleItemRepository;
import br.thullyoo.gerenciador_estoque_backend.repository.SaleRepository;
import br.thullyoo.gerenciador_estoque_backend.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Transactional
    public void registerSale(List<SaleItemRequest> saleItemRequestList) {
        User user = jwtUtils.getUserByToken();

        double total = 0.0;

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setSeller(user);
        sale.setTotalAmount(0.0);
        sale = saleRepository.save(sale);

        List<SaleItem> saleItemList = new ArrayList<>();

        for (SaleItemRequest saleItemRequest : saleItemRequestList) {
            Product product = productRepository.findById(saleItemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < saleItemRequest.quantity()) {
                throw new RuntimeException("Product quantity unavailable");
            }

            product.setQuantity(product.getQuantity() - saleItemRequest.quantity());

            SaleItem saleItem = SaleMapper.toSaleItem(
                    saleItemRequest.quantity(),
                    product.getPrice(),
                    product,
                    sale
            );

            total += product.getPrice() * saleItemRequest.quantity();

            saleItemList.add(saleItem);
            saleItemRepository.save(saleItem);
            productRepository.save(product);
        }

        sale.setItems(saleItemList);
        sale.setTotalAmount(total);
        saleRepository.save(sale);
    }

    public List<SaleResponse> listSalesByUser() {
        User user = jwtUtils.getUserByToken();

        List<Sale> sales = saleRepository.findBySeller(user);

        if (sales.size() <= 0){
            throw new RuntimeException("User doesn't have a sale");
        }

        return sales.stream().map(SaleMapper::toSaleResponse).toList();
    }
}
