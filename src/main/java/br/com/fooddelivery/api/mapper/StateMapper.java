package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.output.StateOutput;
import br.com.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public StateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StateOutput toOutput(State state) {
        return modelMapper.map(state, StateOutput.class);
    }

    public List<StateOutput> toCollectionOutput(List<State> states) {
        return states.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }
}
