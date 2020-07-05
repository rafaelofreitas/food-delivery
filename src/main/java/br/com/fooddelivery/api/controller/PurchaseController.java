package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.PurchaseEntry;
import br.com.fooddelivery.api.dto.output.PurchaseOutput;
import br.com.fooddelivery.api.dto.output.PurchaseSummaryOutput;
import br.com.fooddelivery.api.mapper.OrderItemMapper;
import br.com.fooddelivery.api.mapper.PurchaseMapper;
import br.com.fooddelivery.api.mapper.PurchaseSummaryMapper;
import br.com.fooddelivery.domain.model.User;
import br.com.fooddelivery.domain.service.PurchaseService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseSummaryMapper purchaseSummaryMapper;
    private final OrderItemMapper orderItemMapper;

    public PurchaseController(PurchaseService purchaseService, PurchaseMapper purchaseMapper, PurchaseSummaryMapper purchaseSummaryMapper, OrderItemMapper orderItemMapper) {
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
        this.purchaseSummaryMapper = purchaseSummaryMapper;
        this.orderItemMapper = orderItemMapper;
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

    @PostMapping
    public ResponseEntity<PurchaseOutput> savePurchase(@RequestBody @Valid PurchaseEntry purchaseEntry) {
        var purchase = this.purchaseMapper.toDomain(purchaseEntry);

        // TODO pegar usuário autenticado
        purchase.setClient(new User());
        purchase.getClient().setId(1);

        purchase = this.purchaseService.savePurchase(purchase);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(purchase.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.purchaseMapper.toOutput(purchase));
    }
}
