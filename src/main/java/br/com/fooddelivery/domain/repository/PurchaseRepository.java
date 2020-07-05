package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("from Purchase p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
    List<Purchase> findAll();
}
