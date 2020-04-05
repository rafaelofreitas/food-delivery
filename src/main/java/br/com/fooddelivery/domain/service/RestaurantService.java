package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
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

    public Restaurant pathRestaurant(Integer id, Map<String, Object> dataSource, HttpServletRequest request) {
        var restaurant = this.getRestaurantById(id);

        this.merge(dataSource, restaurant, request);

        return this.updateRestaurant(id, restaurant);
    }

    private void merge(Map<String, Object> dataSource, Restaurant restaurant, HttpServletRequest request) {
        var serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

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
        } catch (IllegalArgumentException e) {
            var rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }
}