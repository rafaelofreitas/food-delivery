package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.CityNotFoundException;
import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.repository.CityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final StateService stateService;

    public CityService(CityRepository cityRepository, StateService stateService) {
        this.cityRepository = cityRepository;
        this.stateService = stateService;
    }

    public Page<City> getCities(Pageable pageable) {
        return this.cityRepository.findAll(pageable);
    }

    public City getCityById(Integer id) {
        return this.cityRepository
                .findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }

    @Transactional
    public City saveCity(City city) {
        Integer stateId = city.getState().getId();

        var state = this.stateService.getStateById(stateId);

        city.setState(state);

        return this.cityRepository.save(city);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.cityRepository.deleteById(id);
            this.cityRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("City cannot be removed as it is in use: %s", id));
        }
    }
}
