package br.com.fooddelivery.domain.repository.impl;

import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProductRepositoryQueriesImpl implements ProductRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto productPhoto) {
        return entityManager.merge(productPhoto);
    }
}
