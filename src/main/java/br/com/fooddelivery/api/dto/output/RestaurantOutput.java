package br.com.fooddelivery.api.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RestaurantOutput {
    private Integer id;
    private String name;
    private BigDecimal freightRate;
    private KitchenOutput kitchen;
    private AddressOutput address;
    private Boolean open;
}