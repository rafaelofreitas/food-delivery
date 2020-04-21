package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.GroupMapper;
import br.com.fooddelivery.api.model.output.GroupOutput;
import br.com.fooddelivery.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {
    private final UserService userService;
    private final GroupMapper groupMapper;

    @Autowired
    public UserGroupController(UserService userService, GroupMapper groupMapper) {
        this.userService = userService;
        this.groupMapper = groupMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupOutput>> getGroups(@PathVariable Integer userId) {
        var user = this.userService.getUserById(userId);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.groupMapper.toCollectionOutput(user.getGroups()));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> associateGroup(@PathVariable Integer userId, @PathVariable Integer groupId) {
        this.userService.associateGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> disassociateGroup(@PathVariable Integer userId, @PathVariable Integer groupId) {
        this.userService.disassociateGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }
}