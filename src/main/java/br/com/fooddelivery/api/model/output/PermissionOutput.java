package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionOutput {
    private Integer id;
    private String name;
    private String description;
}