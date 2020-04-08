package br.com.fooddelivery.api.model;

import br.com.fooddelivery.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class KitchenMixin {
    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
