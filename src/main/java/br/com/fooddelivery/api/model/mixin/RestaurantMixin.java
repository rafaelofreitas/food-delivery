package br.com.fooddelivery.api.model.mixin;

import br.com.fooddelivery.domain.model.Address;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.model.Payment;
import br.com.fooddelivery.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestaurantMixin {
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Kitchen kitchen;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private OffsetDateTime dateRegister;

    @JsonIgnore
    private OffsetDateTime updateDate;

    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
