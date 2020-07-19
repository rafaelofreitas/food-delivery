package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductPhotoCatalogService {
    private final ProductRepository productRepository;

    public ProductPhotoCatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductPhoto saveProductPhoto(ProductPhoto productPhoto) {
        // Delete photo before a new one persists
        Optional<ProductPhoto> existingPhoto = this.productRepository
                .findByProductPhotoId(productPhoto.getRestaurantId(), productPhoto.getProductId());

        existingPhoto.ifPresent(this.productRepository::delete);

        return this.productRepository.save(productPhoto);
    }
}
