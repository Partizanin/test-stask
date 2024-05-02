package com.clear.solution.webapp.service;

import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.repository.UserRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Value("${minimum-required-age}")
    private Long MINIMUM_YEARS_REQUIRED = 21L;

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

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userAgeLessThan(User user) {
        long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        System.out.println("years: " + years);
        System.out.println("MINIMUM_YEARS_REQUIRED " + MINIMUM_YEARS_REQUIRED);
        return years < MINIMUM_YEARS_REQUIRED;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
