package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.CityNotFoundException;
import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private CityRepository cityRepository;
    private StateService stateService;

    @Autowired
    public CityService(CityRepository cityRepository, StateService stateService) {
        this.cityRepository = cityRepository;
        this.stateService = stateService;
    }

    public List<City> getCities() {
        return this.cityRepository.findAll();
    }

    public City getCityById(Integer id) {
        return this.cityRepository
                .findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }

    public City saveCity(City city) {
        Integer stateId = city.getState().getId();

        State state = this.stateService.getStateById(stateId);

        city.setState(state);

        return this.cityRepository.save(city);
    }

    public City updateCity(Integer id, City city) {
        City citySaved = this.getCityById(id);

        BeanUtils.copyProperties(city, citySaved, "id");

        return this.saveCity(citySaved);
    }

    public void deleteById(Integer id) {
        try {
            this.cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("City cannot be removed as it is in use: %s", id));
        }
    }
}
