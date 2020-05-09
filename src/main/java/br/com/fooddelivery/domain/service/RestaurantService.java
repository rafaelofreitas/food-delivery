package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.RestaurantNotFoundException;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public RestaurantService(
            RestaurantRepository restaurantRepository,
            KitchenService kitchenService,
            CityService cityService,
            PaymentService paymentService,
            UserService userService
    ) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenService = kitchenService;
        this.cityService = cityService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public List<Restaurant> getRestaurants() {
        return this.restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer id) {
        return this.restaurantRepository
                .findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        var kitchenId = restaurant.getKitchen().getId();
        var cityId = restaurant.getAddress().getCity().getId();

        var kitchen = this.kitchenService.getKitchenById(kitchenId);
        var city = this.cityService.getCityById(cityId);

        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);

        return this.restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activate(Integer id) {
        var restaurant = this.getRestaurantById(id);

        restaurant.activate();
    }

    @Transactional
    public void activate(List<Integer> restaurantIds) {
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void inactivate(Integer id) {
        var restaurant = this.getRestaurantById(id);

        restaurant.inactivate();
    }

    @Transactional
    public void inactivate(List<Integer> restaurantIds) {
        restaurantIds.forEach(this::inactivate);
    }

    @Transactional
    public void addPayment(Integer restaurantId, Integer paymentId) {
        var restaurant = this.getRestaurantById(restaurantId);

        var payment = this.paymentService.getPaymentById(paymentId);

        restaurant.addPayment(payment);
    }

    @Transactional
    public void deletePayment(Integer restaurantId, Integer paymentId) {
        var restaurant = this.getRestaurantById(restaurantId);

        var payment = this.paymentService.getPaymentById(paymentId);

        restaurant.removePayment(payment);
    }

    @Transactional
    public void open(Integer id) {
        Restaurant currentRestaurant = this.getRestaurantById(id);

        currentRestaurant.open();
    }

    @Transactional
    public void close(Integer id) {
        Restaurant currentRestaurant = this.getRestaurantById(id);

        currentRestaurant.close();
    }

    @Transactional
    public void associateResponsible(Integer restaurantId, Integer userId) {
        var restaurant = this.getRestaurantById(restaurantId);

        var user = this.userService.getUserById(userId);

        restaurant.addResponsible(user);
    }

    @Transactional
    public void disassociateResponsible(Integer restaurantId, Integer userId) {
        var restaurant = this.getRestaurantById(restaurantId);

        var user = this.userService.getUserById(userId);

        restaurant.removeResponsible(user);
    }
}