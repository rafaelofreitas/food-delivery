package br.com.fooddelivery.domain.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PurchaseOrderFlowService {
    private final PurchaseService purchaseService;
    private final SendingEmailService sendingEmailService;

    public PurchaseOrderFlowService(PurchaseService purchaseService, SendingEmailService sendingEmailService) {
        this.purchaseService = purchaseService;
        this.sendingEmailService = sendingEmailService;
    }

    @Transactional
    public void confirmedPurchase(UUID purchaseCode) {
        var purchase = this.purchaseService.getByPurchaseCode(purchaseCode);

        purchase.confirmed();

        var message = SendingEmailService.Message.builder()
                .subjectMatter(purchase.getRestaurant().getName() + " - Pedido Confirmado")
                .body("The code request <strong>" + purchase.getPurchaseCode() + "</strong> has been confirmed!")
                .recipient(purchase.getClient().getEmail())
                .build();

        this.sendingEmailService.send(message);
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
