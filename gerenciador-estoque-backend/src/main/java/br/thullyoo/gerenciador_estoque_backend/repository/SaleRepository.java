package br.thullyoo.gerenciador_estoque_backend.repository;

import br.thullyoo.gerenciador_estoque_backend.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
