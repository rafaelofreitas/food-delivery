package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.KitchenEntry;
import br.com.fooddelivery.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public KitchenMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Kitchen toDomainObject(KitchenEntry kitchenEntry) {
        return this.modelMapper.map(kitchenEntry, Kitchen.class);
    }

    public void copyToDomainObject(KitchenEntry kitchenEntry, Kitchen kitchen) {
        this.modelMapper.map(kitchenEntry, kitchen);
    }
}