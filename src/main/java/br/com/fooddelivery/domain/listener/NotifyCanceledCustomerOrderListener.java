package br.com.fooddelivery.domain.listener;

import br.com.fooddelivery.domain.event.PurchaseCanceledEvent;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.service.SendingEmailService;
import br.com.fooddelivery.domain.service.SendingEmailService.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotifyCanceledCustomerOrderListener {
    private final SendingEmailService sendingEmailService;

    public NotifyCanceledCustomerOrderListener(SendingEmailService sendingEmailService) {
        this.sendingEmailService = sendingEmailService;
    }

    @TransactionalEventListener
    public void whenPurchaseCanceled(PurchaseCanceledEvent purchaseCanceledEvent) {
        Purchase purchase = purchaseCanceledEvent.getPurchase();

        var message = Message.builder()
                .subjectMatter(purchase.getRestaurant().getName() + " - Purchase has been canceled!")
                .variable("purchase", purchase)
                .body("order-cancellation.html")
                .recipient(purchase.getClient().getEmail())
                .build();

        this.sendingEmailService.send(message);
    }
}
