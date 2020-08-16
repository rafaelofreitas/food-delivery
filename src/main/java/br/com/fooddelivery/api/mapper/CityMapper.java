package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.controller.CityController;
import br.com.fooddelivery.api.controller.StateController;
import br.com.fooddelivery.api.dto.entry.CityEntry;
import br.com.fooddelivery.api.dto.output.CityOutput;
import br.com.fooddelivery.domain.model.City;
import br.com.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityMapper extends RepresentationModelAssemblerSupport<City, CityOutput> {
    private final ModelMapper modelMapper;

    public CityMapper(ModelMapper modelMapper) {
        super(CityController.class, CityOutput.class);

        this.modelMapper = modelMapper;
    }

    @Override
    public CityOutput toModel(City city) {
        CityOutput cityOutput = super.createModelWithId(city.getId(), city);

        this.modelMapper.map(city, cityOutput);

        cityOutput.add(linkTo(methodOn(CityController.class)
                .getCityById(cityOutput.getId()))
                .withSelfRel());

        cityOutput.add(linkTo(methodOn(CityController.class)
                .getCities())
                .withRel(IanaLinkRelations.COLLECTION));

        cityOutput.add(linkTo(methodOn(StateController.class)
                .getStateById(cityOutput.getState().getId()))
                .withSelfRel());

        return cityOutput;
    }

    @Override
    public CollectionModel<CityOutput> toCollectionModel(Iterable<? extends City> cities) {
        return super.toCollectionModel(cities)
                .add(linkTo(CityController.class).withSelfRel());
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