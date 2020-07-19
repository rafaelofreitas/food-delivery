package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.PhotoProductEntry;
import br.com.fooddelivery.api.dto.output.ProductPhotoOutput;
import br.com.fooddelivery.api.mapper.ProductPhotoMapper;
import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.service.ProductPhotoCatalogService;
import br.com.fooddelivery.domain.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
    private final ProductService productService;
    private final ProductPhotoCatalogService productPhotoCatalogService;
    private final ProductPhotoMapper productPhotoMapper;

    public RestaurantProductPhotoController(
            ProductService productService,
            ProductPhotoCatalogService productPhotoCatalogService,
            ProductPhotoMapper productPhotoMapper
    ) {
        this.productService = productService;
        this.productPhotoCatalogService = productPhotoCatalogService;
        this.productPhotoMapper = productPhotoMapper;
    }

    @PutMapping(path = "/{productId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductPhotoOutput> updateProductPhoto(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId,
            @Valid PhotoProductEntry photoProductEntry
    ) {
        var product = this.productService.getProductById(restaurantId, productId);
        var file = photoProductEntry.getFile();

        ProductPhoto productPhoto = ProductPhoto.builder()
                .product(product)
                .fileName(file.getOriginalFilename())
                .description(photoProductEntry.getDescription())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

        productPhoto = this.productPhotoCatalogService.saveProductPhoto(productPhoto);

        return ResponseEntity.ok().body(this.productPhotoMapper.toOutput(productPhoto));
    }
}
