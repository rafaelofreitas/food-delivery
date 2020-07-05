package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.output.PurchaseSummaryOutput;
import br.com.fooddelivery.domain.model.Purchase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseSummaryMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseSummaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PurchaseSummaryOutput toOutput(Purchase purchase) {
        return this.modelMapper.map(purchase, PurchaseSummaryOutput.class);
    }

    public List<PurchaseSummaryOutput> toCollectionOutput(Collection<Purchase> purchases) {
        return purchases
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }
}