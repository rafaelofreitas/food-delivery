package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
        return this.restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        Integer kitchenId = restaurant.getKitchen().getId();

        Kitchen kitchen = this.kitchenService.getKitchenById(kitchenId);

        restaurant.setKitchen(kitchen);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Integer id, Restaurant restaurant) {
        Restaurant restaurantSaved = this.getRestaurantById(id);

        BeanUtils.copyProperties(restaurant, restaurantSaved, "id", "payment", "address", "dateRegister", "products");

        return this.saveRestaurant(restaurantSaved);
    }

    public Restaurant pathRestaurant(Integer id, Map<String, Object> dataSource) {
        Restaurant restaurant = this.getRestaurantById(id);

        this.merge(dataSource, restaurant);

        return this.updateRestaurant(id, restaurant);
    }

    private void merge(Map<String, Object> dataSource, Restaurant restaurant) {
        ObjectMapper objectMapper = new ObjectMapper();

        Restaurant newRestaurant = objectMapper.convertValue(dataSource, Restaurant.class);

        dataSource.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, key);

            if (field == null) {
                return;
            }

            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, newRestaurant);

            ReflectionUtils.setField(field, restaurant, newValue);
        });
    }
}
