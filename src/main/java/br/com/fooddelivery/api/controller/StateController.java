package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.StateMapper;
import br.com.fooddelivery.api.model.entry.StateEntry;
import br.com.fooddelivery.api.model.output.StateOutput;
import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/states")
public class StateController {
    private final StateService stateService;
    private final StateMapper stateMapper;

    @Autowired
    public StateController(StateService stateService, StateMapper stateMapper) {
        this.stateService = stateService;
        this.stateMapper = stateMapper;
    }

    @GetMapping
    public ResponseEntity<List<StateOutput>> getCities() {
        List<StateOutput> states = this.stateMapper.toCollectionOutput(this.stateService.getStates());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(states);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateOutput> getStateById(@PathVariable Integer id) {
        var state = this.stateService.getStateById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.stateMapper.toOutput(state));
    }

    @PostMapping
    public ResponseEntity<StateOutput> saveState(@Valid @RequestBody StateEntry stateEntry) {
        State state = this.stateService.saveState(this.stateMapper.toDomain(stateEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(state.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.stateMapper.toOutput(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateOutput> updateState(@PathVariable Integer id, @Valid @RequestBody StateEntry stateEntry) {
        var state = this.stateService.getStateById(id);

        this.stateMapper.copyPropertiesToDomain(stateEntry, state);

        state = this.stateService.saveState(state);

        return ResponseEntity.ok().body(this.stateMapper.toOutput(state));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteState(@PathVariable Integer id) {
        this.stateService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}