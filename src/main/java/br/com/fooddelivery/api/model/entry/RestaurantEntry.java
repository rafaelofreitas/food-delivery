package br.com.fooddelivery.api.model.entry;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
public class RestaurantEntry {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal freightRate;

    @Valid
    @NotNull
    private KitchenIdEntry kitchenIdEntry;
}