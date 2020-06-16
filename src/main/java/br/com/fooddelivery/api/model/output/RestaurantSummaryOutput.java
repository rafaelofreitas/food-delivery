package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantSummaryOutput {
    private Integer id;
    private String name;
}