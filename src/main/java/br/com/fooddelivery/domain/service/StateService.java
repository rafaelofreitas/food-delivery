package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.StateNotFoundException;
import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return this.stateRepository
                .findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));
    }

    @Transactional
    public State saveState(State state) {
        return this.stateRepository.save(state);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("State cannot be removed as it is in use: %s", id));
        }
    }
}
