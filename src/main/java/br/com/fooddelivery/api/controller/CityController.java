package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.CityMapper;
import br.com.fooddelivery.api.dto.entry.CityEntry;
import br.com.fooddelivery.api.dto.output.CityOutput;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.service.CityService;
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
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    @Autowired
    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public ResponseEntity<List<CityOutput>> getCities() {
        List<CityOutput> cities = this.cityMapper.toCollectionOutput(this.cityService.getCities());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityOutput> getCityById(@PathVariable Integer id) {
        var city = this.cityService.getCityById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.cityMapper.toOutput(city));
    }

    @PostMapping
    public ResponseEntity<CityOutput> saveCity(@Valid @RequestBody CityEntry cityEntry) {
        City city = this.cityService.saveCity(this.cityMapper.toDomain(cityEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.cityMapper.toOutput(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityOutput> updateCity(@PathVariable Integer id, @Valid @RequestBody CityEntry cityEntry) {
        var city = this.cityService.getCityById(id);

        this.cityMapper.copyPropertiesToDomain(cityEntry, city);

        city = this.cityService.saveCity(city);

        return ResponseEntity.ok().body(this.cityMapper.toOutput(city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        this.cityService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
