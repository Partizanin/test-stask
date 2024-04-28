package com.clear.solution.webapp.service;

import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public Optional<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public Optional<User> findByAddress(String address) {
        return userRepository.findByAddress(address);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findByBirthDate(LocalDate birthDate) {
        return userRepository.findByBirthDate(birthDate);
    }

    public Optional<List<User>> findByBirthDateRange(LocalDate from, LocalDate to) {
        return userRepository.findByBirthDateBetween(from, to);
    }
}
