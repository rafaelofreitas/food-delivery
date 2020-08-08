package br.com.fooddelivery.domain.event;

import br.com.fooddelivery.domain.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseCanceledEvent {
    private Purchase purchase;
}