package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}
