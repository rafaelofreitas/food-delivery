package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.ProductEntry;
import br.com.fooddelivery.api.dto.output.ProductOutput;
import br.com.fooddelivery.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductOutput toOutput(Product product) {
        return this.modelMapper.map(product, ProductOutput.class);
    }

    public List<ProductOutput> toCollectionOutput(Collection<Product> products) {
        return products
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Product toDomain(ProductEntry productEntry) {
        return this.modelMapper.map(productEntry, Product.class);
    }

    public void copyPropertiesToDomain(ProductEntry productEntry, Product product) {
        this.modelMapper.map(productEntry, product);
    }
}
