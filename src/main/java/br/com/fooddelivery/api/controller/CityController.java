package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.ResourceUriHelper;
import br.com.fooddelivery.api.dto.entry.CityEntry;
import br.com.fooddelivery.api.dto.output.CityOutput;
import br.com.fooddelivery.api.mapper.CityMapper;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.service.CityService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<CityOutput>> getCities() {
        CollectionModel<CityOutput> cityOutputs = this.cityMapper.toCollectionModel(this.cityService.getCities());

        CacheControl cache = CacheControl
                .maxAge(20, TimeUnit.SECONDS)
                .cachePublic();

        return ResponseEntity.ok().cacheControl(cache).body(cityOutputs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityOutput> getCityById(@PathVariable Integer id) {
        var city = this.cityService.getCityById(id);

        CityOutput cityOutput = this.cityMapper.toModel(city);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(cityOutput);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityOutput saveCity(@Valid @RequestBody CityEntry cityEntry) {
        City city = this.cityService.saveCity(this.cityMapper.toDomain(cityEntry));

        ResourceUriHelper.addUriInResponseHeader(city.getId());

        return this.cityMapper.toModel(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityOutput> updateCity(@PathVariable Integer id, @Valid @RequestBody CityEntry cityEntry) {
        var city = this.cityService.getCityById(id);

        this.cityMapper.copyPropertiesToDomain(cityEntry, city);

        city = this.cityService.saveCity(city);

        return ResponseEntity.ok().body(this.cityMapper.toModel(city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        this.cityService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
