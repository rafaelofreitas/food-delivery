package br.com.fooddelivery.domain.repository.impl;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.service.SalesQueryService;

import javax.persistence.EntityManager;
import java.util.List;

public class SalesQueryServiceImpl implements SalesQueryService {
    private final EntityManager entityManager;

    public SalesQueryServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<DailySales> consultDailySales(DailySalesFilter dailySalesFilter) {
        return null;
    }
}
