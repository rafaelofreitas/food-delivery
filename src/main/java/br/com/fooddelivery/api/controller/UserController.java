package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.ResourceUriHelper;
import br.com.fooddelivery.api.dto.entry.PasswordEntry;
import br.com.fooddelivery.api.dto.entry.UserEntry;
import br.com.fooddelivery.api.dto.output.UserOutput;
import br.com.fooddelivery.api.mapper.UserMapper;
import br.com.fooddelivery.domain.model.User;
import br.com.fooddelivery.domain.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserOutput>> getUsers() {
        CollectionModel<UserOutput> users = this.userMapper.toCollectionModel(this.userService.getUsers());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable Integer id) {
        var user = this.userService.getUserById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.userMapper.toModel(user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput saveUser(@Valid @RequestBody UserEntry userEntry) {
        User user = this.userService.saveUser(this.userMapper.toDomain(userEntry));

        ResourceUriHelper.addUriInResponseHeader(user.getId());

        return this.userMapper.toModel(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutput> updateUser(@PathVariable Integer id, @Valid @RequestBody UserEntry userEntry) {
        var user = this.userService.getUserById(id);

        this.userMapper.copyPropertiesToDomain(userEntry, user);

        user = this.userService.saveUser(user);

        return ResponseEntity.ok().body(this.userMapper.toModel(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        this.userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody @Valid PasswordEntry passwordEntry) {
        this.userService.changePassword(id, passwordEntry.getCurrentPassword(), passwordEntry.getNewPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}