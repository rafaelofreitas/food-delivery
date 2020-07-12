package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.dto.entry.StateEntry;
import br.com.fooddelivery.api.dto.output.StateOutput;
import br.com.fooddelivery.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateMapper {
    private final ModelMapper modelMapper;

    public StateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StateOutput toOutput(State state) {
        return this.modelMapper.map(state, StateOutput.class);
    }

    public List<StateOutput> toCollectionOutput(List<State> states) {
        return states
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public State toDomain(StateEntry stateEntry) {
        return this.modelMapper.map(stateEntry, State.class);
    }

    public void copyPropertiesToDomain(StateEntry stateEntry, State state) {
        this.modelMapper.map(stateEntry, state);
    }
}
