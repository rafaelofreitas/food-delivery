package br.com.fooddelivery.api.dto.entry;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PurchaseEntry {
    @Valid
    @NotNull
    private AddressEntry deliveryAddress;

    @Valid
    @NotNull
    private RestaurantIdEntry restaurant;

    @Valid
    @NotNull
    private PaymentIdEntry payment;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<OrderItemEntry> items;
}