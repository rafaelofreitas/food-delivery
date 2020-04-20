package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOutput {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
