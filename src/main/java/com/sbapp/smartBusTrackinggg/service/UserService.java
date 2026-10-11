package com.sbapp.smartBusTrackinggg.service;

import com.sbapp.smartBusTrackinggg.entity.User;
import com.sbapp.smartBusTrackinggg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }


    // Add User
    public boolean addUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setT(user.getOrganisationName());
            userRepository.save(user);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error in addUser service : " + e
            );

            return false;
        }
    }


    // Check Username
    public boolean chkUsername(String userName) {

        return userRepository.findByUserName(userName) != null;
    }


    // Check User Login
    public boolean chkUser(User user) {

        User existingUser =
                userRepository.findByUserName(user.getUserName());

        if (existingUser == null) {
            return false;
        }

        return passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
    }


    // Find User
    public User findUser(String userName) {

        return userRepository.findByUserName(userName);
    }


    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    // Update User
    public boolean saveUser(User user) {

        User existingUser =
                userRepository.findByUserName(user.getUserName());

        if (existingUser == null) {
            return false;
        }

        if (user.getActive() != null) {
            existingUser.setActive(user.getActive());
        }

        if (user.getAccepted() != null) {
            existingUser.setAccepted(user.getAccepted());
        }

        userRepository.save(existingUser);

        return true;
    }


    // Delete User
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public String getJWT(User existingUser) {
        return jwtService.generateToken(existingUser.getT(),existingUser.getRole());
    }
    public boolean chkOrganisationName(String organisationName) {
    return userRepository.findByOrganisationName(organisationName) != null;
}
}
