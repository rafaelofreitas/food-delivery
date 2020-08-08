package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.ResourceUriHelper;
import br.com.fooddelivery.api.dto.entry.CityEntry;
import br.com.fooddelivery.api.dto.output.CityOutput;
import br.com.fooddelivery.api.mapper.CityMapper;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<Page<CityOutput>> getCities(@PageableDefault Pageable pageable) {
        Page<City> cityPage = this.cityService.getCities(pageable);

        List<CityOutput> cityOutputs = this.cityMapper.toCollectionOutput(cityPage.getContent());

        Page<CityOutput> cityOutputPage = new PageImpl<>(cityOutputs, pageable, cityPage.getTotalElements());

        CacheControl cache = CacheControl
                .maxAge(20, TimeUnit.SECONDS)
                .cachePublic();

        return ResponseEntity.ok().cacheControl(cache).body(cityOutputPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityOutput> getCityById(@PathVariable Integer id) {
        var city = this.cityService.getCityById(id);

        CityOutput cityOutput = this.cityMapper.toOutput(city);

//        cityOutput.add(new Link());

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

        return this.cityMapper.toOutput(city);
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
