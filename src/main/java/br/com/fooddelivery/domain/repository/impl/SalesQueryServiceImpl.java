package br.com.fooddelivery.domain.repository.impl;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.repository.SalesQueryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class SalesQueryServiceImpl implements SalesQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySales> consultDailySales(DailySalesFilter filter) {
        var builder = this.entityManager.getCriteriaBuilder();
        var query = builder.createQuery(DailySales.class);
        var root = query.from(Purchase.class);

        var functionDateCreationDate = builder.function("date", Date.class, root.get("creationDate"));

        var selection = builder.construct(
                DailySales.class,
                functionDateCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("subtotal"))
        );

        query.select(selection);
        query.groupBy(functionDateCreationDate);

        return entityManager.createQuery(query).getResultList();
    }
}
