package br.com.fooddelivery.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public abstract class UserMixin {
    @JsonIgnore
    private LocalDateTime dateRegister;
}
