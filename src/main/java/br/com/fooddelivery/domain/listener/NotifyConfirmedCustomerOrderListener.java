package br.com.fooddelivery.domain.listener;

import br.com.fooddelivery.domain.event.PurchaseConfirmedEvent;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.service.SendingEmailService;
import br.com.fooddelivery.domain.service.SendingEmailService.Message;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotifyConfirmedCustomerOrderListener {
    private final SendingEmailService sendingEmailService;

    public NotifyConfirmedCustomerOrderListener(SendingEmailService sendingEmailService) {
        this.sendingEmailService = sendingEmailService;
    }

    @EventListener
    public void whenPurchaseConfirmed(PurchaseConfirmedEvent purchaseConfirmedEvent) {
        Purchase purchase = purchaseConfirmedEvent.getPurchase();

        var message = Message.builder()
                .subjectMatter(purchase.getRestaurant().getName() + " - Purchase has been confirmed!")
                .variable("purchase", purchase)
                .body("order-confirmed.html")
                .recipient(purchase.getClient().getEmail())
                .build();

        this.sendingEmailService.send(message);
    }
}
