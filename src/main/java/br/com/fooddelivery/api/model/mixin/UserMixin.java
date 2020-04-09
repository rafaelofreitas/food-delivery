package br.com.fooddelivery.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.OffsetDateTime;

public abstract class UserMixin {
    @JsonIgnore
    private OffsetDateTime dateRegister;
}
