package com.prueba.ApiRestPrueba.controller;

import com.prueba.ApiRestPrueba.model.User;
import com.prueba.ApiRestPrueba.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers (
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter) {

        if(filter != null && !filter.isEmpty()) {
            return ResponseEntity.ok(userService.findAllFiltered(filter));
        }
        return ResponseEntity.ok(userService.findAll(sortedBy));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User update = userService.update(id, user);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}