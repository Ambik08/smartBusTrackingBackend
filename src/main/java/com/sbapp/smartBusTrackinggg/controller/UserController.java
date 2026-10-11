package com.sbapp.smartBusTrackinggg.controller;

import com.sbapp.smartBusTrackinggg.entity.User;
import com.sbapp.smartBusTrackinggg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Signup
    @PostMapping("/signup")
    public ResponseEntity<?> addUser(
            @RequestBody User user) {

        if (user == null ||
                user.getOrganisationName() == null ||
                user.getEmail() == null ||
                user.getPhoneNumber() == null ||
                user.getUserName() == null ||
                user.getPassword() == null ||
                user.getOrganisationName().isBlank() ||
                user.getEmail().isBlank() ||
                user.getPhoneNumber().isBlank() ||
                user.getUserName().isBlank() ||
                user.getPassword().isBlank()) {

            return new ResponseEntity<>(
                    "All fields are required",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (userService.chkUsername(
                user.getUserName())) {

            return new ResponseEntity<>(
                    "Username already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (userService.addUser(user)) {

            return new ResponseEntity<>(
                    "User successfully created",
                    HttpStatus.CREATED
            );
        }

        return new ResponseEntity<>(
                "Error in adding user",
                HttpStatus.BAD_REQUEST
        );
    }


    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User user) {

        if (user == null ||
                user.getUserName() == null ||
                user.getPassword() == null ||
                user.getUserName().isBlank() ||
                user.getPassword().isBlank()) {

            return new ResponseEntity<>(
                    "All fields are required",
                    HttpStatus.BAD_REQUEST
            );
        }

        User existingUser =
                userService.findUser(user.getUserName());

        if (existingUser == null) {

            return new ResponseEntity<>(
                    "User doesn't exist, try signup",
                    HttpStatus.NOT_FOUND
            );
        }

        if (!userService.chkUser(user)) {

            return new ResponseEntity<>(
                    "Password Incorrect",
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (!existingUser.getAccepted()) {

            return new ResponseEntity<>(
                    "Please wait for admin approval",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (!existingUser.getActive()) {

            return new ResponseEntity<>(
                    "Your validity expired, contact admin",
                    HttpStatus.BAD_REQUEST
            );
        }
        String jwtToken=userService.getJWT(existingUser);
        Map<String, String> response = new HashMap<>();

        response.put("name", existingUser.getOrganisationName());
        response.put("token",jwtToken);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // Get All Users
    @GetMapping
    public ResponseEntity<?> getUsers() {

        List<User> users =
                userService.getAllUsers();

        if (users.isEmpty()) {

            return new ResponseEntity<>(
                    "No users found",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                users,
                HttpStatus.OK
        );
    }


    // Update User
    @PostMapping
    public ResponseEntity<?> saveUser(
            @RequestBody User user) {

        if (userService.saveUser(user)) {

            return new ResponseEntity<>(
                    "User successfully saved",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                "User could not be saved",
                HttpStatus.NOT_FOUND
        );
    }


    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        try {

            userService.deleteUser(id);

            return new ResponseEntity<>(
                    "User deleted successfully",
                    HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error in deleting user",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}