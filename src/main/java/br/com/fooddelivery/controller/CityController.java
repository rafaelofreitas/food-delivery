package br.com.fooddelivery.controller;

import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getCities() {
        return this.cityService.getCities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Integer id) {
        City city = this.cityService.getCityById(id);

        return ResponseEntity.ok().body(city);
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@Valid @RequestBody City city) {
        city = this.cityService.saveCity(city);

        return ResponseEntity.status(HttpStatus.CREATED).body(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Integer id, @Valid @RequestBody City city) {
        city = this.cityService.updateCity(id, city);

        return ResponseEntity.ok().body(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id) {
        this.cityService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
