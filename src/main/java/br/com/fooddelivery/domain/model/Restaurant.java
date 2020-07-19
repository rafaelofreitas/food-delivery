package br.com.fooddelivery.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String name;

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

    @ManyToMany
    @JoinTable(
            name = "tb_user_restaurant_responsible",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> responsible = new HashSet<>();

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

    public boolean addResponsible(User user) {
        return this.getResponsible().add(user);
    }

    public boolean removeResponsible(User user) {
        return this.getResponsible().remove(user);
    }

    public boolean acceptedShapePayment(Payment payment) {
        return this.getPayments().contains(payment);
    }

    public boolean noAcceptShapePayment(Payment payment) {
        return !this.acceptedShapePayment(payment);
    }
}
