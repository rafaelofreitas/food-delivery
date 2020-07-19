package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductPhotoCatalogService {
    private final ProductRepository productRepository;

    public ProductPhotoCatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductPhoto saveProductPhoto(ProductPhoto productPhoto) {
        return this.productRepository.save(productPhoto);
    }
}
