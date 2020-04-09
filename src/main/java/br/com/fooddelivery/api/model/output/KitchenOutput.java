package br.com.fooddelivery.api.model.output;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KitchenOutput {
    private Integer id;
    private String name;
}