package br.com.fooddelivery.api.dto.output;

import br.com.fooddelivery.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KitchenOutput {
    @JsonView(RestaurantView.Resume.class)
    private Integer id;

    @JsonView(RestaurantView.Resume.class)
    private String name;
}