package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.output.PurchaseOutput;
import br.com.fooddelivery.domain.model.Purchase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PurchaseOutput toOutput(Purchase purchase) {
        return this.modelMapper.map(purchase, PurchaseOutput.class);
    }

    public List<PurchaseOutput> toCollectionOutput(Collection<Purchase> purchases) {
        return purchases
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }
}