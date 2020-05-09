package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.RestaurantMapper;
import br.com.fooddelivery.api.model.entry.RestaurantEntry;
import br.com.fooddelivery.api.model.output.RestaurantOutput;
import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantOutput>> getRestaurants() {
        List<RestaurantOutput> restaurants = this.restaurantMapper.toCollectionOutput(this.restaurantService.getRestaurants());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantOutput> getRestaurantById(@PathVariable Integer id) {
        var restaurant = this.restaurantService.getRestaurantById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.restaurantMapper.toOutput(restaurant));
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

    @PutMapping("/{id}/active")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        this.restaurantService.activate(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/active-restaurants")
    public ResponseEntity<?> activate(@RequestBody List<Integer> ids) {
        try {
            this.restaurantService.activate(ids);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/active")
    public ResponseEntity<?> inactivate(@PathVariable Integer id) {
        this.restaurantService.inactivate(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/active-restaurants")
    public ResponseEntity<?> inactivate(@RequestBody List<Integer> ids) {
        try {
            this.restaurantService.inactivate(ids);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/opening")
    public ResponseEntity<?> open(@PathVariable Integer id) {
        this.restaurantService.open(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<?> close(@PathVariable Integer id) {
        this.restaurantService.close(id);

        return ResponseEntity.noContent().build();
    }
}