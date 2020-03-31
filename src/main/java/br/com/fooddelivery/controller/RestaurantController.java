package br.com.fooddelivery.controller;

import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getRestaurants() {
        return this.restaurantService.getRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);

        return ResponseEntity.ok().body(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> saveRestaurant(@RequestBody Restaurant restaurant) {
        restaurant = restaurantService.saveRestaurant(restaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Integer id, @RequestBody Restaurant restaurant) {
        restaurant = this.restaurantService.updateRestaurant(id, restaurant);

        return ResponseEntity.ok().body(restaurant);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> pathRestaurant(@PathVariable Integer id, @RequestBody Map<String, Object> dataSource) {
        Restaurant restaurant = this.restaurantService.pathRestaurant(id, dataSource);

        return ResponseEntity.ok().body(restaurant);
    }
}
