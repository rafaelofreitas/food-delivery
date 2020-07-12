package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SalesQueryRepository {
    List<DailySales> consultDailySales(DailySalesFilter dailySalesFilter, String timeOffset);
}
