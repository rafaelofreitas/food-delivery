package br.com.fooddelivery;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.KitchenRepository;
import br.com.fooddelivery.domain.repository.RestaurantRepository;
import br.com.fooddelivery.domain.service.KitchenService;
import br.com.fooddelivery.utils.DatabaseCleaner;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class KitchenServiceIT {
    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        this.databaseCleaner.clearTables();
        this.prepareData();
    }

    @Test
    public void mustAssignId_WhenRegisteringKitchenWithCorrectData() {
        Kitchen kitchen = Kitchen.builder()
                .name("Chinese")
                .build();

        kitchen = this.kitchenService.saveKitchen(kitchen);

        Assertions.assertThat(kitchen).isNotNull();
        Assertions.assertThat(kitchen.getId()).isNotNull();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void mustFail_WhenToRegisterUnnamedKitchen() {
        Kitchen kitchen = Kitchen.builder()
                .name(null)
                .build();

        this.kitchenService.saveKitchen(kitchen);
    }

    @Test(expected = EntityInUseException.class)
    public void mustFail_WhenExcludingKitchenInUse() {
        this.kitchenService.deleteKitchenById(1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mustFail_WhenExcludeExistingKitchen() {
        this.kitchenService.deleteKitchenById(Integer.MAX_VALUE);
    }

    private void prepareData() {
        Kitchen kitchen = Kitchen.builder()
                .name("Tailandesa")
                .build();

        this.kitchenRepository.save(kitchen);
        this.saveRestaurant(kitchen);

        Kitchen kitchen2 = Kitchen.builder()
                .name("Americana")
                .build();

        this.kitchenRepository.save(kitchen2);
    }

    private void saveRestaurant(Kitchen kitchen) {
        Restaurant restaurant = Restaurant.builder()
                .freightRate(BigDecimal.valueOf(15))
                .name("Dona Nina")
                .active(true)
                .open(true)
                .kitchen(kitchen)
                .build();

        this.restaurantRepository.save(restaurant);
    }
}
