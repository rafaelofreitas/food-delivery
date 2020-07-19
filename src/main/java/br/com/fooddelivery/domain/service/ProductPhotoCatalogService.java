package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class ProductPhotoCatalogService {
    private final ProductRepository productRepository;
    private final PhotoStorageService photoStorageService;

    public ProductPhotoCatalogService(ProductRepository productRepository, PhotoStorageService photoStorageService) {
        this.productRepository = productRepository;
        this.photoStorageService = photoStorageService;
    }

    @Transactional
    public ProductPhoto saveProductPhoto(ProductPhoto productPhoto, InputStream fileData) {
        // Delete photo before a new one persists
        Optional<ProductPhoto> existingPhoto = this.productRepository
                .findByProductPhotoId(productPhoto.getRestaurantId(), productPhoto.getProductId());

        existingPhoto.ifPresent(this.productRepository::delete);

        productPhoto = this.productRepository.save(productPhoto);

        this.productRepository.flush();

        var newPicture = PhotoStorageService.NewPicture.builder()
                .fileName(productPhoto.getFileName())
                .inputStream(fileData)
                .build();

        this.photoStorageService.store(newPicture);

        return productPhoto;
    }
}
