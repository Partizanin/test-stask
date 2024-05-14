package com.clear.solution.webapp.service;

import com.clear.solution.webapp.component.Patcher;
import com.clear.solution.webapp.exception.UserAddressNotFoundException;
import com.clear.solution.webapp.exception.UserBirthDateNotFoundException;
import com.clear.solution.webapp.exception.UserBirthDateRangeBadRequestException;
import com.clear.solution.webapp.exception.UserBirthDateRangeNotFoundException;
import com.clear.solution.webapp.exception.UserEmailNotFoundException;
import com.clear.solution.webapp.exception.UserFirstNameNotFoundException;
import com.clear.solution.webapp.exception.UserIdNotFoundException;
import com.clear.solution.webapp.exception.UserLastNameNotFoundException;
import com.clear.solution.webapp.exception.UserPhoneNumberNotFoundException;
import com.clear.solution.webapp.exception.UserRequiredAgeException;
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
    @Value("${min-required-age}")
    private Integer MIN_YEARS_REQUIRED = 21;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserIdNotFoundException(id));

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));

    }

    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).
                orElseThrow(() -> new UserFirstNameNotFoundException(firstName));

    }

    public User findByLastName(String lastName) {
        return userRepository.findByLastName(lastName).
                orElseThrow(() -> new UserLastNameNotFoundException(lastName));
    }

    public User findByAddress(String address) {
        return userRepository.findByAddress(address).
                orElseThrow(() -> new UserAddressNotFoundException(address));
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UserPhoneNumberNotFoundException(phoneNumber));
    }

    public User findByBirthDate(LocalDate birthDate) {
        return userRepository.findByBirthDate(birthDate).
                orElseThrow(() -> new UserBirthDateNotFoundException(birthDate));
    }

    public List<User> findByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new UserBirthDateRangeBadRequestException(from, to);
        }
        return userRepository.findByBirthDateBetween(from, to)
                .orElseThrow(() -> new UserBirthDateRangeNotFoundException(from, to));
    }

    private boolean userAgeLessThanRequired(User user) {
        long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        System.out.println("MINIMUM_YEARS_REQUIRED " + MIN_YEARS_REQUIRED);
        return years < MIN_YEARS_REQUIRED;
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(
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
    }

    public User patchUser(User patchUser, Long id) {
        Optional<User> userByIdOpt = userRepository.findById(id);

        if (userByIdOpt.isPresent()) {
            User userById = userByIdOpt.get();
            try {
                Patcher.userPatcher(userById, patchUser);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return userRepository.save(userById);
        } else {
            throw new UserIdNotFoundException(patchUser.getId());
        }
    }

    public void deleteUser(Long id) {
        Optional<User> userByIdOpt = userRepository.findById(id);

        if (userByIdOpt.isEmpty()) {
            throw new UserIdNotFoundException(id);
        } else {
            userRepository.delete(userByIdOpt.get());
        }
    }

    public User createUser(User user) {
        if (userAgeLessThanRequired(user)) {
            throw new UserRequiredAgeException(MIN_YEARS_REQUIRED);
        }
        return userRepository.save(user);
    }
}
