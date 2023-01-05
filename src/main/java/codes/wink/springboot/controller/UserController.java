package codes.wink.springboot.controller;

import lombok.AllArgsConstructor;

import codes.wink.springboot.entity.User;
import codes.wink.springboot.service.UserService;

import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
@SuppressWarnings("rawtypes")
public class UserController {

    private UserService userService;

    // build create User REST API
    // http://localhost:8080/api/users
    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        if (user.firstName != null && user.lastName != null && user.email != null) {
            User savedUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Missing parameters in body");
        }
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long userId) {
        User user;
        try {
            user = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body("User not found");
        }
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users

    @GetMapping
    public ResponseEntity getAllUsers() {
        JSONArray users = userService.getAllUsers();
        if (users.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("There are no users yet");
        }
    }

    // Build Update User REST API
    // http://localhost:8080/api/users/1
    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long userId, @RequestBody User user) throws Exception {
        user.setId(userId);
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body("User not found");
        }
    }

    // Build Delete User REST API
    // http://localhost:8080/api/users/1
    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long userId) throws Exception {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body("User not found");
        }
    }
}