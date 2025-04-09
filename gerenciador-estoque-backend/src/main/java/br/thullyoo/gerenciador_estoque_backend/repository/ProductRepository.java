package br.thullyoo.gerenciador_estoque_backend.repository;

import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
