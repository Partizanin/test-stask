package com.clear.solution.webapp.controller;

import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.service.UserService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {


    private final UserService userservice;

    @Autowired
    public UsersController(UserService userservice) {
        this.userservice = userservice;
    }

// get mapping

    @GetMapping()
    ResponseEntity<List<User>> getAllUsers() {
        return userservice.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userservice.findById(id);
    }

    @GetMapping(params = "email")
    ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userservice.findByEmail(email);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<User> getUserByFirstName(@RequestParam String firstName) {
        return userservice.findByFirstName(firstName);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<User> getUserByLastName(@RequestParam String lastName) {
        return userservice.findByLastName(lastName);
    }

    @GetMapping(params = "address")
    ResponseEntity<User> getUserByAddress(@RequestParam String address) {
        return userservice.findByAddress(address);
    }

    @GetMapping(params = "phoneNumber")
    ResponseEntity<User> getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return userservice.findByPhoneNumber(phoneNumber);
    }

    @GetMapping(params = "birthDate")
    ResponseEntity<User> getUserByBirthDate(@RequestParam LocalDate birthDate) {
        return userservice.findByBirthDate(birthDate);
    }

    @GetMapping(params = {"from", "to"})
    ResponseEntity<List<User>> getUserByBirthDateRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return userservice.findByBirthDateRange(from, to);
    }

//    post mapping

    @PostMapping()
    ResponseEntity<User> createUser(@RequestBody User user) {
        return userservice.createUser(user);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userservice.updateUser(id, user);
    }

    @PatchMapping()
    ResponseEntity<User> patchUser(@RequestBody User patchUser) {
        return userservice.patchUser(patchUser);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return userservice.deleteUser(id);
    }
}
