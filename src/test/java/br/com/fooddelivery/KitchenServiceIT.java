package br.com.fooddelivery;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.service.KitchenService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KitchenServiceIT {
    @Autowired
    private KitchenService kitchenService;

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
}
