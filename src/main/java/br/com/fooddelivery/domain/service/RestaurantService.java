package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private KitchenService kitchenService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, KitchenService kitchenService) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenService = kitchenService;
    }

    public List<Restaurant> getRestaurants() {
        return this.restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer id) {
        return this.restaurantRepository
                .findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        var kitchenId = restaurant.getKitchen().getId();

        var kitchen = this.kitchenService.getKitchenById(kitchenId);

        restaurant.setKitchen(kitchen);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Integer id, Restaurant restaurant) {
        var restaurantSaved = this.getRestaurantById(id);

        BeanUtils.copyProperties(restaurant, restaurantSaved, "id", "payment", "address", "dateRegister", "products");

        return this.saveRestaurant(restaurantSaved);
    }
}