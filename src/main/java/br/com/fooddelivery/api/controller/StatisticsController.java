package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.domain.filter.DailySalesFilter;
import br.com.fooddelivery.domain.model.aggregate.DailySales;
import br.com.fooddelivery.domain.service.DailySalesReportService;
import br.com.fooddelivery.domain.service.SalesQueryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {
    private final SalesQueryService salesQueryService;
    private final DailySalesReportService dailySalesReportService;

    public StatisticsController(SalesQueryService salesQueryService, DailySalesReportService dailySalesReportService) {
        this.salesQueryService = salesQueryService;
        this.dailySalesReportService = dailySalesReportService;
    }

    @GetMapping(path = "daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySales> consultDailySales(
            DailySalesFilter filter,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        return this.salesQueryService.consultDailySales(filter, timeOffset);
    }

    @GetMapping(path = "daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultDailySalesPdf(
            DailySalesFilter filter,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        var dailySales = this.dailySalesReportService.issueDailySales(filter, timeOffset);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf")
                .body(dailySales);
    }
}
