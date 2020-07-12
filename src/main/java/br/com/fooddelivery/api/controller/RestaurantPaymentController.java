package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.output.PaymentOutput;
import br.com.fooddelivery.api.mapper.PaymentMapper;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payments")
public class RestaurantPaymentController {
    private final RestaurantService restaurantService;
    private final PaymentMapper paymentMapper;

    public RestaurantPaymentController(RestaurantService restaurantService, PaymentMapper paymentMapper) {
        this.restaurantService = restaurantService;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping
    public ResponseEntity<List<PaymentOutput>> getRestaurantPayment(@PathVariable Integer restaurantId) {
        var restaurant = this.restaurantService.getRestaurantById(restaurantId);

        List<PaymentOutput> paymentOutputs = this.paymentMapper.toCollectionOutput(restaurant.getPayments());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(paymentOutputs);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<?> addRestaurantPayment(@PathVariable Integer restaurantId, @PathVariable Integer paymentId) {
        this.restaurantService.addPayment(restaurantId, paymentId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deleteRestaurantPayment(@PathVariable Integer restaurantId, @PathVariable Integer paymentId) {
        this.restaurantService.deletePayment(restaurantId, paymentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}