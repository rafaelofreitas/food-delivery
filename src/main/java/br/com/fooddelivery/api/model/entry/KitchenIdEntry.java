package br.com.fooddelivery.api.model.entry;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class KitchenIdEntry {
    @NotNull
    private Integer id;
}