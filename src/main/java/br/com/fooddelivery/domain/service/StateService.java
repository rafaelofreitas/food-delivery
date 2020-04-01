package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getStates() {
        return this.stateRepository.findAll();
    }

    public State getStateById(Integer id) {
        return this.stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Não existe cadastro de estados com código %d", id)));
    }

    public State saveState(State state) {
        return this.stateRepository.save(state);
    }

    public State updateState(Integer id, State state) {
        State stateSaved = this.getStateById(id);

        BeanUtils.copyProperties(state, stateSaved, "id");

        return this.saveState(stateSaved);
    }

    public void deleteById(Integer id) {
        State state = this.getStateById(id);

        this.stateRepository.delete(state);
    }
}
