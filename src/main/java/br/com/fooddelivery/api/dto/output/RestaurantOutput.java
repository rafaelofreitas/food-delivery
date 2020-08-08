package br.com.fooddelivery.api.dto.output;

import br.com.fooddelivery.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RestaurantOutput extends RepresentationModel<RestaurantOutput> {
    @JsonView({RestaurantView.Resume.class, RestaurantView.OnlyName.class})
    private Integer id;

    @JsonView({RestaurantView.Resume.class, RestaurantView.OnlyName.class})
    private String name;

    @JsonView(RestaurantView.Resume.class)
    private BigDecimal freightRate;

    @JsonView(RestaurantView.Resume.class)
    private KitchenOutput kitchen;

    private AddressOutput address;

    private Boolean open;
}