package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.CityEntry;
import br.com.fooddelivery.api.dto.output.CityOutput;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public CityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityOutput toOutput(City city) {
        return this.modelMapper.map(city, CityOutput.class);
    }

    public List<CityOutput> toCollectionOutput(List<City> cities) {
        return cities
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public City toDomain(CityEntry cityEntry) {
        return this.modelMapper.map(cityEntry, City.class);
    }

    public void copyPropertiesToDomain(CityEntry cityEntry, City city) {
        // So that we can reference a new state for a city
        // Without JPA understanding that we are changing the state ID.
        city.setState(new State());

        this.modelMapper.map(cityEntry, city);
    }
}