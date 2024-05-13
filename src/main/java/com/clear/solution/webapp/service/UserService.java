package com.clear.solution.webapp.service;

import com.clear.solution.webapp.component.Patcher;
import com.clear.solution.webapp.exception.UserAddressNotFoundException;
import com.clear.solution.webapp.exception.UserBirthDateNotFoundException;
import com.clear.solution.webapp.exception.UserBirthDateRangeNotFoundException;
import com.clear.solution.webapp.exception.UserEmailNotFoundException;
import com.clear.solution.webapp.exception.UserFirstNameNotFoundException;
import com.clear.solution.webapp.exception.UserIdNotFoundException;
import com.clear.solution.webapp.exception.UserLastNameNotFoundException;
import com.clear.solution.webapp.exception.UserPhoneNumberNotFoundException;
import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.repository.UserRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<User> findById(Long id) {
        return ResponseEntity.ok(userRepository.findById(id).
                orElseThrow(() -> new UserIdNotFoundException(id)));
    }

    public ResponseEntity<User> findByEmail(String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email)));
    }

    public ResponseEntity<User> findByFirstName(String firstName) {
        return ResponseEntity.ok(userRepository.findByFirstName(firstName).
                orElseThrow(() -> new UserFirstNameNotFoundException(firstName)));
    }

    public ResponseEntity<User> findByLastName(String lastName) {
        return ResponseEntity.ok(userRepository.findByLastName(lastName).
                orElseThrow(() -> new UserLastNameNotFoundException(lastName)));

    }

    public ResponseEntity<User> findByAddress(String address) {
        return ResponseEntity.ok(userRepository.findByAddress(address).
                orElseThrow(() -> new UserAddressNotFoundException(address)));
    }

    public ResponseEntity<User> findByPhoneNumber(String phoneNumber) {
        return ResponseEntity.ok(userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UserPhoneNumberNotFoundException(phoneNumber)));
    }

    public ResponseEntity<User> findByBirthDate(LocalDate birthDate) {
        return ResponseEntity.ok(userRepository.findByBirthDate(birthDate).
                orElseThrow(() -> new UserBirthDateNotFoundException(birthDate)));
    }

    public ResponseEntity<List<User>> findByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userRepository.findByBirthDateBetween(from, to)
                .orElseThrow(() -> new UserBirthDateRangeNotFoundException(from, to)));
    }

    private boolean userAgeLessThan(User user) {
        long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        System.out.println("years: " + years);
        System.out.println("MINIMUM_YEARS_REQUIRED " + MINIMUM_YEARS_REQUIRED);
        return years < MINIMUM_YEARS_REQUIRED;
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        User body = userRepository.findById(id).map(
                userFromDb -> {
                    userFromDb.setFirstName(user.getFirstName());
                    userFromDb.setLastName(user.getLastName());
                    userFromDb.setAddress(user.getAddress());
                    userFromDb.setBirthDate(user.getBirthDate());
                    userFromDb.setEmail(user.getEmail());
                    userFromDb.setPhoneNumber(user.getPhoneNumber());
                    return userRepository.save(userFromDb);
                }
        ).orElseThrow(() -> new UserIdNotFoundException(id));

        return ResponseEntity.ok(body);
    }

    public ResponseEntity<User> patchUser(User patchUser) {
        Optional<User> userByIdOpt = userRepository.findById(patchUser.getId());

        if (userByIdOpt.isPresent()) {
            User userById = userByIdOpt.get();
            try {
                Patcher.userPatcher(userById, patchUser);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok(userRepository.save(userById));
        } else {
            throw new UserIdNotFoundException(patchUser.getId());
        }
    }

    public ResponseEntity<User> deleteUser(Long id) {
        Optional<User> userByIdOpt = userRepository.findById(id);

        if (userByIdOpt.isEmpty()) {
            throw new UserIdNotFoundException(id);
        } else {
            userRepository.delete(userByIdOpt.get());
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<User> createUser(User user) {
        System.out.println("createUser mapping");
        if (userAgeLessThan(user)) {
            System.out.println("bad request");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }
}
