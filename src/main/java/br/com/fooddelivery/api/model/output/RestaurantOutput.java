package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantOutput {
    private Integer id;
    private String name;
    private BigDecimal freightRate;
    private KitchenOutput kitchen;
    private AddressOutput address;
    private Boolean open;
}