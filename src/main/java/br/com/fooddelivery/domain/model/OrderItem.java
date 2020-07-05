package br.com.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tb_order_item")
public class OrderItem {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void calculatePriceTotal() {
        BigDecimal unitPrice = this.getUnitPrice();
        Integer amount = this.getAmount();

        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        if (amount == null) {
            amount = 0;
        }

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(amount)));
    }
}
