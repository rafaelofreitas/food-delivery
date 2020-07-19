package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.ProductPhotoNotFoundException;
import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

        if (existingPhoto.isPresent()) {
            this.photoStorageService.delete(existingPhoto.get().getFileName());
            this.productRepository.delete(existingPhoto.get());
        }

        String fileName = this.photoStorageService.getGenerateFileName(productPhoto.getFileName());
        productPhoto.setFileName(fileName);

        productPhoto = this.productRepository.save(productPhoto);
        this.productRepository.flush();

        var newPicture = PhotoStorageService.NewPicture.builder()
                .fileName(productPhoto.getFileName())
                .inputStream(fileData)
                .build();

        this.photoStorageService.store(newPicture);

        return productPhoto;
    }

    public ProductPhoto getProductPhotoById(Integer restaurantId, Integer productId) {
        return this.productRepository
                .findByProductPhotoId(restaurantId, productId)
                .orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
    }

    @Transactional
    public void deleteProductPhotoById(Integer restaurantId, Integer productId) {
        try {
            var productPhoto = this.getProductPhotoById(restaurantId, productId);

            this.productRepository.delete(productPhoto);

            this.productRepository.flush();

            this.photoStorageService.delete(productPhoto.getFileName());
        } catch (EmptyResultDataAccessException e) {
            throw new ProductPhotoNotFoundException(restaurantId, productId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException("Product Photo cannot be removed as it is in use!");
        }
    }
}
