package br.com.fooddelivery.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("fooddelivery.email")
public class EmailProperties {
    private Implementation impl;

    @NotNull
    private String sender;

    public enum Implementation {
        SMTP, FAKE
    }
}
