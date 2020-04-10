package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.RestaurantMapper;
import br.com.fooddelivery.api.model.entry.RestaurantEntry;
import br.com.fooddelivery.api.model.output.RestaurantOutput;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;
    private RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping
    public List<RestaurantOutput> getRestaurants() {
        var restaurants = this.restaurantService.getRestaurants();

        return this.restaurantMapper.toCollectionOutput(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantOutput> getRestaurantById(@PathVariable Integer id) {
        var restaurant = restaurantService.getRestaurantById(id);

        return ResponseEntity.ok().body(this.restaurantMapper.toOutput(restaurant));
    }

    @PostMapping
    public ResponseEntity<RestaurantOutput> saveRestaurant(@Valid @RequestBody RestaurantEntry restaurantEntry) {
        Restaurant restaurant = this.restaurantService.saveRestaurant(this.restaurantMapper.toDomain(restaurantEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(restaurant.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.restaurantMapper.toOutput(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantOutput> updateRestaurant(@PathVariable Integer id, @Valid @RequestBody RestaurantEntry restaurantEntry) {
        var restaurant = this.restaurantService.getRestaurantById(id);

        this.restaurantMapper.copyPropertiesToDomain(restaurantEntry, restaurant);

        restaurant = this.restaurantService.saveRestaurant(restaurant);

        return ResponseEntity.ok().body(this.restaurantMapper.toOutput(restaurant));
    }
}
