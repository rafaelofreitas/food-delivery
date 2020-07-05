package br.com.fooddelivery.api.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressOutput {
    private String zipCode;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private CityResumeOutput city;
}