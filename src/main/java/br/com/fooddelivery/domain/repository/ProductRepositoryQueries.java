package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.ProductPhoto;

public interface ProductRepositoryQueries {
    ProductPhoto save(ProductPhoto productPhoto);

    void delete(ProductPhoto productPhoto);
}
