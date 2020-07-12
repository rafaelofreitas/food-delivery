package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.RestaurantEntry;
import br.com.fooddelivery.api.dto.output.RestaurantOutput;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {
    private final ModelMapper modelMapper;

    public RestaurantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantOutput toOutput(Restaurant restaurant) {
        return this.modelMapper.map(restaurant, RestaurantOutput.class);
    }

    public List<RestaurantOutput> toCollectionOutput(List<Restaurant> restaurants) {
        return restaurants
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Restaurant toDomain(RestaurantEntry restaurantEntry) {
        return this.modelMapper.map(restaurantEntry, Restaurant.class);
    }

    public void copyPropertiesToDomain(RestaurantEntry restaurantEntry, Restaurant restaurant) {
        //So that we can reference a new kitchen for a restaurant
        //Without JPA understanding that we are changing the kitchen ID.
        restaurant.setKitchen(new Kitchen());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        this.modelMapper.map(restaurantEntry, restaurant);
    }
}
