package br.com.fooddelivery.domain.event;

import br.com.fooddelivery.domain.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseConfirmedEvent {
    private Purchase purchase;
}

/*
        var message = SendingEmailService.Message.builder()
                .subjectMatter(purchase.getRestaurant().getName() + " - Purchase has been confirmed!")
                .variable("purchase", purchase)
                .body("order-confirmed.html")
                .recipient(purchase.getClient().getEmail())
                .build();

        this.sendingEmailService.send(message);
 */