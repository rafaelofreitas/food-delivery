package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.KitchenEntry;
import br.com.fooddelivery.api.model.output.KitchenOutput;
import br.com.fooddelivery.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KitchenMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public KitchenMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public KitchenOutput toOutput(Kitchen kitchen) {
        return this.modelMapper.map(kitchen, KitchenOutput.class);
    }

    public List<KitchenOutput> toCollectionOutput(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Kitchen toDomain(KitchenEntry kitchenEntry) {
        return this.modelMapper.map(kitchenEntry, Kitchen.class);
    }

    public void copyPropertiesToDomain(KitchenEntry kitchenEntry, Kitchen kitchen) {
        this.modelMapper.map(kitchenEntry, kitchen);
    }
}