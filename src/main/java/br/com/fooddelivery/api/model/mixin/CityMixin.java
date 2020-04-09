package br.com.fooddelivery.api.model.mixin;

import br.com.fooddelivery.domain.model.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CityMixin {
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
