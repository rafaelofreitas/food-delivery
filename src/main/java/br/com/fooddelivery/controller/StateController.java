package br.com.fooddelivery.controller;

import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {
    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public List<State> getCities() {
        return this.stateService.getStates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Integer id) {
        State state = this.stateService.getStateById(id);

        return ResponseEntity.ok().body(state);
    }

    @PostMapping
    public ResponseEntity<State> saveState(@Valid @RequestBody State state) {
        state = this.stateService.saveState(state);

        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(@PathVariable Integer id, @RequestBody State state) {
        state = this.stateService.updateState(id, state);

        return ResponseEntity.ok().body(state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id) {
        this.stateService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
