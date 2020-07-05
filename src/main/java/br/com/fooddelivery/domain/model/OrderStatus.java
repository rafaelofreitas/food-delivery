package br.com.fooddelivery.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELED("Canceled", CREATED, CONFIRMED);

    private final String description;
    private final List<OrderStatus> previousStatus;

    OrderStatus(String description, OrderStatus... previousStatus) {
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public boolean noCanChangeTo(OrderStatus newStatus) {
        return !newStatus.previousStatus.contains(this);
    }
}