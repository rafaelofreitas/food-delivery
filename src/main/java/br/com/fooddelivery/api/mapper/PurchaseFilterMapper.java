package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.PurchaseFilterEntry;
import br.com.fooddelivery.domain.filter.PurchaseFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseFilterMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseFilterMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PurchaseFilter toDomain(PurchaseFilterEntry purchaseFilterEntry) {
        return this.modelMapper.map(purchaseFilterEntry, PurchaseFilter.class);
    }
}