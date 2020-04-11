package br.com.fooddelivery.api.model.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantEntry {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal freightRate;

    @Valid
    @NotNull
    private KitchenIdEntry kitchen;
}