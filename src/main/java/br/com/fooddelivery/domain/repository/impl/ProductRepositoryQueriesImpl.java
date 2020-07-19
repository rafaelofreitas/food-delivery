package br.com.fooddelivery.domain.repository.impl;

import br.com.fooddelivery.domain.model.ProductPhoto;
import br.com.fooddelivery.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryQueriesImpl implements ProductRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto productPhoto) {
        return this.entityManager.merge(productPhoto);
    }

    @Transactional
    @Override
    public void delete(ProductPhoto productPhoto) {
        this.entityManager.remove(productPhoto);
    }
}
