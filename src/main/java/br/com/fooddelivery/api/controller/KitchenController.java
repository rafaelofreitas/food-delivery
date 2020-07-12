package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.KitchenEntry;
import br.com.fooddelivery.api.dto.output.KitchenOutput;
import br.com.fooddelivery.api.mapper.KitchenMapper;
import br.com.fooddelivery.domain.model.Kitchen;
import br.com.fooddelivery.domain.service.KitchenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final KitchenService kitchenService;
    private final KitchenMapper kitchenMapper;

    public KitchenController(KitchenService kitchenService, KitchenMapper kitchenMapper) {
        this.kitchenService = kitchenService;
        this.kitchenMapper = kitchenMapper;
    }

    @GetMapping
    public ResponseEntity<Page<KitchenOutput>> getKitchens(@PageableDefault Pageable pageable) {
        Page<Kitchen> kitchenPage = this.kitchenService.getKitchens(pageable);

        List<KitchenOutput> kitchenOutputs = this.kitchenMapper.toCollectionOutput(kitchenPage.getContent());

        Page<KitchenOutput> kitchenOutputPage = new PageImpl<>(kitchenOutputs, pageable, kitchenPage.getTotalElements());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(kitchenOutputPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenOutput> getKitchenById(@PathVariable Integer id) {
        var kitchen = this.kitchenService.getKitchenById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.kitchenMapper.toOutput(kitchen));
    }

    @PostMapping
    public ResponseEntity<KitchenOutput> createKitchen(@Valid @RequestBody KitchenEntry kitchenEntry) {
        Kitchen kitchen = this.kitchenService.saveKitchen(this.kitchenMapper.toDomain(kitchenEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(kitchen.getId())
                .toUri();


        return ResponseEntity.created(uri).body(this.kitchenMapper.toOutput(kitchen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenOutput> updateKitchen(
            @PathVariable Integer id,
            @Valid @RequestBody KitchenEntry kitchenEntry
    ) {
        var kitchen = this.kitchenService.getKitchenById(id);

        this.kitchenMapper.copyPropertiesToDomain(kitchenEntry, kitchen);

        kitchen = this.kitchenService.saveKitchen(kitchen);

        return ResponseEntity.ok().body(this.kitchenMapper.toOutput(kitchen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKitchen(@PathVariable Integer id) {
        this.kitchenService.deleteKitchenById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
