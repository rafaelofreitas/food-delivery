package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.service.SalesQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {
    private final SalesQueryService salesQueryService;

    public StatisticsController(SalesQueryService salesQueryService) {
        this.salesQueryService = salesQueryService;
    }

    @GetMapping
    public List<DailySales> consultDailySales(
            DailySalesFilter filter,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        return this.salesQueryService.consultDailySales(filter, timeOffset);
    }
}
