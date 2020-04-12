package br.com.fooddelivery.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOutput {
    private Integer id;
    private String name;
    private String email;
}
