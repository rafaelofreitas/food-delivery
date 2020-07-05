package br.com.fooddelivery.api.dto.entry;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RestaurantEntry {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal freightRate;

    @Valid
    @NotNull
    private KitchenIdEntry kitchen;

    @Valid
    @NotNull
    private AddressEntry address;
}