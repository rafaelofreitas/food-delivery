package br.com.fooddelivery.api.dto.entry;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressEntry {
    @NotBlank
    private String zipCode;

    @NotBlank
    private String publicPlace;

    @NotBlank
    private String number;

    private String complement;

    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityIdEntry city;
}
