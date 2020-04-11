package br.com.fooddelivery.core.modelmapper;

import br.com.fooddelivery.api.model.output.AddressOutput;
import br.com.fooddelivery.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var addressToAddressOutputTypeMap = modelMapper.createTypeMap(Address.class, AddressOutput.class);

        addressToAddressOutputTypeMap.<String>addMapping(
                src -> src.getCity().getState().getName(),
                (dest, value) -> dest.getCity().setState(value)
        );

        return modelMapper;
    }
}
