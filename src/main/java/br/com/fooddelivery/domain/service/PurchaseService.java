package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.PurchaseNotFoundException;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase getById(Integer id) {
        return this.purchaseRepository
                .findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException(id));
    }

    public List<Purchase> getPurchases() {
        return this.purchaseRepository.findAll();
    }
}
