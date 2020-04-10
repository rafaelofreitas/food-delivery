package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.output.CityOutput;
import br.com.fooddelivery.domain.model.City;
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
        return modelMapper.map(city, CityOutput.class);
    }

    public List<CityOutput> toCollectionOutput(List<City> cities) {
        return cities.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }
}