package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return this.cityRepository.findAll();
    }

    public City getCityById(Integer id) {
        return this.cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Não existe cadastro de cidades com código %d", id)));
    }

    public City saveCity(City city) {
        return this.cityRepository.save(city);
    }

    public City updateCity(Integer id, City city) {
        City citySaved = this.getCityById(id);

        BeanUtils.copyProperties(city, citySaved, "id");

        return this.saveCity(citySaved);
    }

    public void deleteById(Integer id) {
        City city = this.getCityById(id);

        this.cityRepository.delete(city);
    }
}
