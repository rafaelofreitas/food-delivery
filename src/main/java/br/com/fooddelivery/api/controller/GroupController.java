package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.GroupMapper;
import br.com.fooddelivery.api.model.entry.GroupEntry;
import br.com.fooddelivery.api.model.output.GroupOutput;
import br.com.fooddelivery.domain.model.Group;
import br.com.fooddelivery.domain.service.GroupService;
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
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupOutput>> getGroups() {
        List<GroupOutput> groups = this.groupMapper.toCollectionOutput(this.groupService.getGroups());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupOutput> getGroupById(@PathVariable Integer id) {
        var group = this.groupService.getGroupById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.groupMapper.toOutput(group));
    }

    @PostMapping
    public ResponseEntity<GroupOutput> saveGroup(@Valid @RequestBody GroupEntry groupEntry) {
        Group group = this.groupService.saveGroup(this.groupMapper.toDomain(groupEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(group.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.groupMapper.toOutput(group));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupOutput> updateGroup(@PathVariable Integer id, @Valid @RequestBody GroupEntry groupEntry) {
        var group = this.groupService.getGroupById(id);

        this.groupMapper.copyPropertiesToDomain(groupEntry, group);

        group = this.groupService.saveGroup(group);

        return ResponseEntity.ok().body(this.groupMapper.toOutput(group));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
        this.groupService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
