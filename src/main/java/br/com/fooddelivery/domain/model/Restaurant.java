package br.com.fooddelivery.domain.model;

import br.com.fooddelivery.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_restaurant")
public class Restaurant {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @PositiveOrZero
    @Column(name = "freigh_rate", nullable = false)
    private BigDecimal freightRate;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateRegister;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    private Boolean active = Boolean.TRUE;

    private Boolean open = Boolean.FALSE;

    public void open() {
        this.setOpen(true);
    }

    public void close() {
        this.setOpen(false);
    }

    @Valid
    @ConvertGroup(to = Groups.KitchenId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tb_restaurant_payment",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id")
    )
    private Set<Payment> payments = new HashSet<>();

    public void activate() {
        this.setActive(true);
    }

    public void inactivate() {
        this.setActive(false);
    }

    public boolean addPayment(Payment payment) {
        return this.getPayments().add(payment);
    }

    public boolean removePayment(Payment payment) {
        return this.getPayments().remove(payment);
    }
}
