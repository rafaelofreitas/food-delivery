package br.com.fooddelivery.api.model.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityEntry {
    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdEntry stateIdEntry;
}
