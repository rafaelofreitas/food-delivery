package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.StateEntry;
import br.com.fooddelivery.api.dto.output.StateOutput;
import br.com.fooddelivery.api.mapper.StateMapper;
import br.com.fooddelivery.domain.model.State;
import br.com.fooddelivery.domain.service.StateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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

    public StateController(StateService stateService, StateMapper stateMapper) {
        this.stateService = stateService;
        this.stateMapper = stateMapper;
    }

    @GetMapping
    public ResponseEntity<Page<StateOutput>> getCities(@PageableDefault Pageable pageable) {
        Page<State> statePage = this.stateService.getStates(pageable);

        List<StateOutput> stateOutputs = this.stateMapper.toCollectionOutput(statePage.getContent());

        Page<StateOutput> stateOutputPage = new PageImpl<>(stateOutputs, pageable, statePage.getTotalElements());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(stateOutputPage);
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

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}