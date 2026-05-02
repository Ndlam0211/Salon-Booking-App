package com.lamnd.controller;

import com.lamnd.common.ApiResponse;
import com.lamnd.common.BaseController;
import com.lamnd.dto.request.UserCreateRequest;
import com.lamnd.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ResponseEntity.ok(createSuccessResponse("User created successfully"));
    }

    @GetMapping
    public  List<User> getUsers() {
        List<User> users = List.of(new User(), new User());

        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        User user = new User();
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = new User();

        return existingUser;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        return "User with id " + id + " has been deleted.";
    }
}
