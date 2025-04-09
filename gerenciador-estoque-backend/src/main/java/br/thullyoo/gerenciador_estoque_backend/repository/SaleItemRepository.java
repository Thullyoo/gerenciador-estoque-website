package br.thullyoo.gerenciador_estoque_backend.repository;

import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
