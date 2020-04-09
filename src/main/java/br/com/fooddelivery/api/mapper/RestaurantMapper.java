package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.RestaurantEntry;
import br.com.fooddelivery.api.model.output.RestaurantOutput;
import br.com.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RestaurantOutput toOutput(Restaurant restaurant) {
        return this.modelMapper.map(restaurant, RestaurantOutput.class);
    }

    public List<RestaurantOutput> toCollectionOutput(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Restaurant toDomain(RestaurantEntry restaurantEntry) {
        return this.modelMapper.map(restaurantEntry, Restaurant.class);
    }
}
