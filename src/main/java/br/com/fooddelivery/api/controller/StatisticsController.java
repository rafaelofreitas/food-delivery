package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.service.SalesQueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {
    private final SalesQueryService salesQueryService;

    public StatisticsController(SalesQueryService salesQueryService) {
        this.salesQueryService = salesQueryService;
    }

    public List<DailySales> consultDailySales(DailySalesFilter filter) {
        return this.salesQueryService.consultDailySales(filter);
    }
}
