package br.com.fooddelivery.api.model.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
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
