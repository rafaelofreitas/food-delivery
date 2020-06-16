package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOutput {
    private Integer id;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancellationDate;
    private OffsetDateTime deliveryDate;
    private String orderStatus;
    private AddressOutput deliveryAddress;
    private PaymentOutput payment;
    private RestaurantSummaryOutput restaurant;
    private UserOutput client;
    private List<OrderItemOutput> items;
}
