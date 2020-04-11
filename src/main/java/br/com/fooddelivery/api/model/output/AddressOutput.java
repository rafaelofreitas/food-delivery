package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressOutput {
    private String zipCode;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private CityResumeOutput city;
}