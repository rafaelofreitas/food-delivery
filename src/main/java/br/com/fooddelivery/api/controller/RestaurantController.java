package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    @Autowired
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
    public ResponseEntity<?> saveRestaurant(@Valid @RequestBody Restaurant restaurant) {
        restaurant = restaurantService.saveRestaurant(restaurant);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(restaurant.getId())
                .toUri();

        return ResponseEntity.created(uri).body(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Integer id, @Valid @RequestBody Restaurant restaurant) {
        restaurant = this.restaurantService.updateRestaurant(id, restaurant);

        return ResponseEntity.ok().body(restaurant);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> pathRestaurant(@PathVariable Integer id, @Valid @RequestBody Map<String, Object> dataSource) {
        Restaurant restaurant = this.restaurantService.pathRestaurant(id, dataSource);

        return ResponseEntity.ok().body(restaurant);
    }
}
