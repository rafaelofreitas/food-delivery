package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.output.UserOutput;
import br.com.fooddelivery.api.mapper.UserMapper;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/restaurants/{restaurantId}/responsibles")
public class RestaurantUserResponsibleController {
    private final RestaurantService restaurantService;
    private final UserMapper userMapper;

    @Autowired
    public RestaurantUserResponsibleController(RestaurantService restaurantService, UserMapper userMapper) {
        this.restaurantService = restaurantService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserOutput>> getResponsible(@PathVariable Integer restaurantId) {
        var restaurant = this.restaurantService.getRestaurantById(restaurantId);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.userMapper.toCollectionOutput(restaurant.getResponsible()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> associateGroup(@PathVariable Integer restaurantId, @PathVariable Integer userId) {
        this.restaurantService.associateResponsible(restaurantId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> disassociateGroup(@PathVariable Integer restaurantId, @PathVariable Integer userId) {
        this.restaurantService.disassociateResponsible(restaurantId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}