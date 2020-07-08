package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.ProductEntry;
import br.com.fooddelivery.api.dto.output.ProductOutput;
import br.com.fooddelivery.api.mapper.ProductMapper;
import br.com.fooddelivery.domain.model.Product;
import br.com.fooddelivery.domain.service.ProductService;
import br.com.fooddelivery.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public RestaurantProductController(
            RestaurantService restaurantService,
            ProductService productService,
            ProductMapper productMapper
    ) {
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductOutput>> getRestaurantProducts(
            @PathVariable Integer restaurantId,
            @RequestParam(required = false) boolean inactive
    ) {
        var restaurant = this.restaurantService.getRestaurantById(restaurantId);

        List<ProductOutput> productOutputs;

        if (inactive) {
            productOutputs = this.productMapper.toCollectionOutput(this.productService.getAllProductsByRestaurant(restaurant));
        } else {
            productOutputs = this.productMapper.toCollectionOutput(this.productService.getProductsByRestaurantActive(restaurant));
        }

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(productOutputs);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductOutput> getProductById(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId
    ) {
        var product = this.productService.getProductById(restaurantId, productId);

        var productOutput = this.productMapper.toOutput(product);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(productOutput);
    }

    @PostMapping
    public ResponseEntity<ProductOutput> saveProduct(
            @PathVariable Integer restaurantId,
            @RequestBody @Valid ProductEntry productEntry
    ) {
        var restaurant = this.restaurantService.getRestaurantById(restaurantId);

        Product product = this.productMapper.toDomain(productEntry);

        product.setRestaurant(restaurant);

        product = this.productService.saveProduct(product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.productMapper.toOutput(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductOutput> updateRestaurant(
            @PathVariable Integer restaurantId,
            @PathVariable Integer productId,
            @Valid @RequestBody ProductEntry productEntry
    ) {
        var product = this.productService.getProductById(restaurantId, productId);

        this.productMapper.copyPropertiesToDomain(productEntry, product);

        product = this.productService.saveProduct(product);

        return ResponseEntity.ok().body(this.productMapper.toOutput(product));
    }
}
