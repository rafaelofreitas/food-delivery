package br.com.fooddelivery.api.model.output;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class RestaurantOutput {
    private Integer id;
    private String name;
    private BigDecimal freightRate;
    private KitchenOutput kitchenOutput;
}