package br.com.fooddelivery.core.modelmapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapperConfig modelMapperConfig() {
        return new ModelMapperConfig();
    }
}
