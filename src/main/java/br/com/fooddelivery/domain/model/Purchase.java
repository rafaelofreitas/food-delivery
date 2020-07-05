package br.com.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tb_purchase")
public class Purchase {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Integer id;

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
    private final OrderStatus orderStatus = OrderStatus.CREATED;

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

    @OneToMany(mappedBy = "purchase")
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotalValue() {
        this.subtotal = this.getItems()
                .stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.amount = this.subtotal.add(this.shippingFee);
    }

    public void setShipping() {
        this.setShippingFee(this.getRestaurant().getFreightRate());
    }

    public void assignOrderToItems() {
        this.getItems().forEach(item -> item.setPurchase(this));
    }
}
