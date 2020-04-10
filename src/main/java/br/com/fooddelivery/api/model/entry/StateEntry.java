package br.com.fooddelivery.api.model.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateEntry {
    @NotBlank
    private String name;
}