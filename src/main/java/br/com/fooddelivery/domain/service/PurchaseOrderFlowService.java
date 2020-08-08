package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PurchaseOrderFlowService {
    private final PurchaseService purchaseService;
    private final PurchaseRepository repository;

    public PurchaseOrderFlowService(PurchaseService purchaseService, PurchaseRepository repository) {
        this.purchaseService = purchaseService;
        this.repository = repository;
    }

    @Transactional
    public void confirmedPurchase(UUID purchaseCode) {
        var purchase = this.purchaseService.getByPurchaseCode(purchaseCode);

        purchase.confirmed();

        this.repository.save(purchase);
    }

    @Transactional
    public void deliveredPurchase(UUID purchaseCode) {
        var purchase = this.purchaseService.getByPurchaseCode(purchaseCode);

        purchase.delivered();
    }

    @Transactional
    public void canceledPurchase(UUID purchaseCode) {
        var purchase = this.purchaseService.getByPurchaseCode(purchaseCode);

        purchase.canceled();
    }
}
