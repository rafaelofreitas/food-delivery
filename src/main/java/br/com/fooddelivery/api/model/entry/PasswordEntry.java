package br.com.fooddelivery.api.model.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordEntry {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}