package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.output.PurchaseOutput;
import br.com.fooddelivery.api.dto.output.PurchaseSummaryOutput;
import br.com.fooddelivery.api.mapper.PurchaseMapper;
import br.com.fooddelivery.api.mapper.PurchaseSummaryMapper;
import br.com.fooddelivery.domain.service.PurchaseService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseSummaryMapper purchaseSummaryMapper;

    public PurchaseController(PurchaseService purchaseService, PurchaseMapper purchaseMapper, PurchaseSummaryMapper purchaseSummaryMapper) {
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
        this.purchaseSummaryMapper = purchaseSummaryMapper;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseSummaryOutput>> getPurchases() {
        List<PurchaseSummaryOutput> purchases = this.purchaseSummaryMapper.toCollectionOutput(this.purchaseService.getPurchases());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOutput> getRestaurantById(@PathVariable Integer id) {
        var purchase = this.purchaseService.getById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.purchaseMapper.toOutput(purchase));
    }
}
