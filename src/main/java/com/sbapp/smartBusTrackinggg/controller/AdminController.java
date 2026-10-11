package com.sbapp.smartBusTrackinggg.controller;

import com.sbapp.smartBusTrackinggg.entity.Admin;
import com.sbapp.smartBusTrackinggg.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final JwtService jwtService;
    public  AdminController(JwtService jwtService){
        this.jwtService=jwtService;
    }

    @PostMapping
    public ResponseEntity<?> chkUser(@RequestBody Admin admin) {

        if (admin == null ||
                admin.getUsername() == null ||
                admin.getPassword() == null ||
                admin.getUsername().isBlank() ||
                admin.getPassword().isBlank()) {

            return new ResponseEntity<>(
                    "Enter all fields",
                    HttpStatus.BAD_REQUEST
            );
        }

        boolean usernameCorrect =
                admin.getUsername().equals("Admin");

        boolean passwordCorrect =
                admin.getPassword().equals("admin");

        if (usernameCorrect && passwordCorrect) {
            String token=jwtService.generateToken("admin","admin");
            System.out.println(token);
            return new ResponseEntity<>(
                    token,
                    HttpStatus.OK
            );
        }

        if (usernameCorrect) {

            return new ResponseEntity<>(
                    "Incorrect password",
                    HttpStatus.UNAUTHORIZED
            );
        }

        return new ResponseEntity<>(
                "Credentials are wrong",
                HttpStatus.UNAUTHORIZED
        );
    }
}