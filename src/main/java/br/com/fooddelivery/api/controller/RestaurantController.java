package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.RestaurantEntry;
import br.com.fooddelivery.api.dto.output.RestaurantOutput;
import br.com.fooddelivery.api.dto.view.RestaurantView;
import br.com.fooddelivery.api.mapper.RestaurantMapper;
import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.service.RestaurantService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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

    @JsonView(RestaurantView.Resume.class)
    @GetMapping(params = "projection=resume")
    public ResponseEntity<List<RestaurantOutput>> getResumeRestaurants() {
        return this.getRestaurants();
    }

    @JsonView(RestaurantView.OnlyName.class)
    @GetMapping(params = "projection=only-name")
    public ResponseEntity<List<RestaurantOutput>> getOnlyNameRestaurants() {
        return this.getRestaurants();
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
    public ResponseEntity<RestaurantOutput> updateRestaurant(
            @PathVariable Integer id,
            @Valid @RequestBody RestaurantEntry restaurantEntry
    ) {
        var restaurant = this.restaurantService.getRestaurantById(id);

        this.restaurantMapper.copyPropertiesToDomain(restaurantEntry, restaurant);

        restaurant = this.restaurantService.saveRestaurant(restaurant);

        return ResponseEntity.ok().body(this.restaurantMapper.toOutput(restaurant));
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        this.restaurantService.activate(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/active-restaurants")
    public ResponseEntity<?> activate(@RequestBody List<Integer> ids) {
        try {
            this.restaurantService.activate(ids);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/active")
    public ResponseEntity<?> inactivate(@PathVariable Integer id) {
        this.restaurantService.inactivate(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/active-restaurants")
    public ResponseEntity<?> inactivate(@RequestBody List<Integer> ids) {
        try {
            this.restaurantService.inactivate(ids);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/opening")
    public ResponseEntity<?> open(@PathVariable Integer id) {
        this.restaurantService.open(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<?> close(@PathVariable Integer id) {
        this.restaurantService.close(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}