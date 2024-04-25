package com.clear.solution.webapp.controller;

import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {


    private final UserRepository repository;

    @Autowired
    public UsersController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/users")
    List<User> getAllUsers() {
        return repository.findAll();
    }

}
