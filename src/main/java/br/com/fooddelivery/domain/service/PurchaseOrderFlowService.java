package br.com.fooddelivery.domain.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PurchaseOrderFlowService {
    private final PurchaseService purchaseService;

    public PurchaseOrderFlowService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Transactional
    public void confirmedPurchase(UUID purchaseCode) {
        var purchase = this.purchaseService.getByPurchaseCode(purchaseCode);

        purchase.confirmed();
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
