package br.com.fooddelivery.domain.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PurchaseOrderFlowService {
    private final PurchaseService purchaseService;

    public PurchaseOrderFlowService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Transactional
    public void confirmedPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        purchase.confirmed();
    }

    @Transactional
    public void deliveredPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        purchase.delivered();
    }

    @Transactional
    public void canceledPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        purchase.canceled();
    }
}
