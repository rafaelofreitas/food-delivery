package br.com.fooddelivery.core.jackson;

import br.com.fooddelivery.api.model.mixin.CityMixin;
import br.com.fooddelivery.api.model.mixin.KitchenMixin;
import br.com.fooddelivery.api.model.mixin.RestaurantMixin;
import br.com.fooddelivery.api.model.mixin.UserMixin;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.model.User;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        this.setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        this.setMixInAnnotation(Kitchen.class, KitchenMixin.class);
        this.setMixInAnnotation(City.class, CityMixin.class);
        this.setMixInAnnotation(User.class, UserMixin.class);
    }
}