package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {
    private KitchenService kitchenService;

    @Autowired
    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping
    public ResponseEntity<List<Kitchen>> getKitchens() {
        List<Kitchen> kitchens = this.kitchenService.getKitchens();

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.status(HttpStatus.OK).cacheControl(cache).body(kitchens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> getKitchenById(@PathVariable Integer id) {
        var kitchen = this.kitchenService.getKitchenById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.status(HttpStatus.OK).cacheControl(cache).body(kitchen);
    }

    @PostMapping
    public ResponseEntity<Kitchen> createKitchen(@Valid @RequestBody Kitchen kitchen) {
        kitchen = this.kitchenService.createKitchen(kitchen);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(kitchen.getId())
                .toUri();

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.created(uri).cacheControl(cache).body(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> updateKitchen(@PathVariable Integer id, @Valid @RequestBody Kitchen kitchen) {
        kitchen = this.kitchenService.updateKitchen(id, kitchen);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(kitchen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKitchen(@PathVariable Integer id) {
        this.kitchenService.deleteKitchenById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
