package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.CityMapper;
import br.com.fooddelivery.api.model.entry.CityEntry;
import br.com.fooddelivery.api.model.output.CityOutput;
import br.com.fooddelivery.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private CityService cityService;
    private CityMapper cityMapper;

    @Autowired
    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public List<CityOutput> getCities() {
        var cities = this.cityService.getCities();

        return this.cityMapper.toCollectionOutput(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityOutput> getCityById(@PathVariable Integer id) {
        var city = this.cityService.getCityById(id);

        return ResponseEntity.ok().body(this.cityMapper.toOutput(city));
    }

    @PostMapping
    public ResponseEntity<CityOutput> saveCity(@Valid @RequestBody CityEntry cityEntry) {
        var city = this.cityService.saveCity(this.cityMapper.toDomain(cityEntry));

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
    public ResponseEntity<?> remover(@PathVariable Integer id) {
        this.cityService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
