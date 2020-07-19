package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.PhotoProductEntry;
import br.com.fooddelivery.api.dto.output.ProductPhotoOutput;
import br.com.fooddelivery.api.mapper.ProductPhotoMapper;
import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.service.PhotoStorageService;
import br.com.fooddelivery.domain.service.ProductPhotoCatalogService;
import br.com.fooddelivery.domain.service.ProductService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
    private final ProductService productService;
    private final ProductPhotoCatalogService productPhotoCatalogService;
    private final ProductPhotoMapper productPhotoMapper;
    private final PhotoStorageService photoStorageService;

    public RestaurantProductPhotoController(
            ProductService productService,
            ProductPhotoCatalogService productPhotoCatalogService,
            ProductPhotoMapper productPhotoMapper,
            PhotoStorageService photoStorageService
    ) {
        this.productService = productService;
        this.productPhotoCatalogService = productPhotoCatalogService;
        this.productPhotoMapper = productPhotoMapper;
        this.photoStorageService = photoStorageService;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductPhotoOutput> updateProductPhoto(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId,
            @Valid PhotoProductEntry photoProductEntry
    ) throws IOException {
        var product = this.productService.getProductById(restaurantId, productId);

        var file = photoProductEntry.getFile();

        ProductPhoto productPhoto = ProductPhoto.builder()
                .product(product)
                .fileName(file.getOriginalFilename())
                .description(photoProductEntry.getDescription())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

        productPhoto = this.productPhotoCatalogService.saveProductPhoto(productPhoto, file.getInputStream());

        return ResponseEntity.ok().body(this.productPhotoMapper.toOutput(productPhoto));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPhotoOutput> getProductPhoto(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId
    ) {
        var productPhoto = this.productPhotoCatalogService.getProductPhotoById(restaurantId, productId);

        return ResponseEntity.ok().body(this.productPhotoMapper.toOutput(productPhoto));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> toRecoverProductPhoto(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId,
            @RequestHeader(name = "accept") String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException {
        try {
            var productPhoto = this.productPhotoCatalogService.getProductPhotoById(restaurantId, productId);

            var mediaTypePhoto = MediaType.parseMediaType(productPhoto.getContentType());
            List<MediaType> mediaTypeAccept = MediaType.parseMediaTypes(acceptHeader);

            this.isAcceptMediaType(mediaTypePhoto, mediaTypeAccept);

            InputStream inputStream = this.photoStorageService.toRecover(productPhoto.getFileName());

            return ResponseEntity
                    .ok()
                    .contentType(mediaTypePhoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void isAcceptMediaType(MediaType mediaType, List<MediaType> accepts) throws HttpMediaTypeNotAcceptableException {
        boolean isAccept = accepts
                .stream()
                .anyMatch(mediaTypeAccept -> mediaTypeAccept.isCompatibleWith(mediaType));

        if (!isAccept) {
            throw new HttpMediaTypeNotAcceptableException(accepts);
        }
    }
}
