package br.com.fooddelivery.domain.model;

import br.com.fooddelivery.domain.event.PurchaseCanceledEvent;
import br.com.fooddelivery.domain.event.PurchaseConfirmedEvent;
import br.com.fooddelivery.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@Entity
@Table(name = "tb_purchase")
public class Purchase extends AbstractAggregateRoot<Purchase> {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Integer id;

    @Column(name = "purchase_code", nullable = false)
    private UUID purchaseCode;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @Column(name = "confirmation_date")
    private OffsetDateTime confirmationDate;

    @Column(name = "cancellation_date")
    private OffsetDateTime cancellationDate;

    @Column(name = "delivery_date")
    private OffsetDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Embedded
    private Address deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotalValue() {
        this.getItems().forEach(OrderItem::calculatePriceTotal);

        this.subtotal = this.getItems()
                .stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.amount = this.subtotal.add(this.shippingFee);
    }

    public void confirmed() {
        this.setOrderStatus(OrderStatus.CONFIRMED);
        this.setConfirmationDate(OffsetDateTime.now());

        this.registerEvent(new PurchaseConfirmedEvent(this));
    }

    public void delivered() {
        this.setOrderStatus(OrderStatus.DELIVERED);
        this.setDeliveryDate(OffsetDateTime.now());
    }

    public void canceled() {
        this.setOrderStatus(OrderStatus.CANCELED);
        this.setCancellationDate(OffsetDateTime.now());

        this.registerEvent(new PurchaseCanceledEvent(this));
    }

    public void setOrderStatus(OrderStatus newStatus) {
        if (this.getOrderStatus().noCanChangeTo(newStatus)) {
            throw new BusinessException(
                    String.format(
                            "Order status %s cannot be changed %s for %s",
                            this.getPurchaseCode(),
                            this.getOrderStatus().getDescription(),
                            newStatus.getDescription()
                    )
            );
        }

        this.orderStatus = newStatus;
    }

    @PrePersist
    private void generateCode() {
        this.setPurchaseCode(UUID.randomUUID());
    }
}
