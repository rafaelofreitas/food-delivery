package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.output.ProductPhotoOutput;
import br.com.fooddelivery.domain.model.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoMapper {
    private final ModelMapper modelMapper;

    public ProductPhotoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductPhotoOutput toOutput(ProductPhoto productPhoto) {
        return this.modelMapper.map(productPhoto, ProductPhotoOutput.class);
    }
}
