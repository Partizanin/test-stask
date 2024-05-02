package com.clear.solution.webapp.controller;

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
import com.clear.solution.webapp.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    List<User> getAllUsers() {
        return userservice.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userservice.findById(id).orElseThrow(() -> new UserIdNotFoundException(id)));
    }

    @GetMapping(params = "email")
    ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userservice.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email)));
    }

    @GetMapping(params = "firstName")
    ResponseEntity<User> getUserByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(userservice.findByFirstName(firstName).orElseThrow(() -> new UserFirstNameNotFoundException(firstName)));
    }

    @GetMapping(params = "lastName")
    ResponseEntity<User> getUserByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(userservice.findByLastName(lastName).orElseThrow(() -> new UserLastNameNotFoundException(lastName)));
    }

    @GetMapping(params = "address")
    ResponseEntity<User> getUserByAddress(@RequestParam String address) {
        return ResponseEntity.ok(userservice.findByAddress(address).orElseThrow(() -> new UserAddressNotFoundException(address)));
    }

    @GetMapping(params = "phoneNumber")
    ResponseEntity<User> getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(userservice.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserPhoneNumberNotFoundException(phoneNumber)));
    }

    @GetMapping(params = "birthDate")
    ResponseEntity<User> getUserByBirthDate(@RequestParam LocalDate birthDate) {
        return ResponseEntity.ok(userservice.findByBirthDate(birthDate).orElseThrow(() -> new UserBirthDateNotFoundException(birthDate)));
    }

    @GetMapping(params = {"from", "to"})
    ResponseEntity<List<User>> getUserByBirthDateRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userservice.findByBirthDateRange(from, to).orElseThrow(() -> new UserBirthDateRangeNotFoundException(from, to)));
    }

//    post mapping

    @PostMapping()
    ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("createUser mapping");
        if (userservice.userAgeLessThan(user)) {
            System.out.println("bad request");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userservice.saveUser(user));
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        System.out.println("updateUser mapping");
        User body = userservice.findById(id).map(
                userFromDb -> {
                    userFromDb.setFirstName(user.getFirstName());
                    userFromDb.setLastName(user.getLastName());
                    userFromDb.setAddress(user.getAddress());
                    userFromDb.setBirthDate(user.getBirthDate());
                    userFromDb.setEmail(user.getEmail());
                    userFromDb.setPhoneNumber(user.getPhoneNumber());
                    return userservice.saveUser(userFromDb);
                }
        ).orElseThrow(() -> new UserIdNotFoundException(id));

        return ResponseEntity.ok(body);
    }

    @PatchMapping()
    ResponseEntity<User> patchUser(@RequestBody User patchUser) {
        Optional<User> userByIdOpt = userservice.findById(patchUser.getId());

        if (userByIdOpt.isPresent()) {
            User userById = userByIdOpt.get();
            try {
                Patcher.userPatcher(userById, patchUser);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok(userservice.saveUser(userById));
        } else {
            throw new UserIdNotFoundException(patchUser.getId());
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userByIdOpt = userservice.findById(id);

        if (userByIdOpt.isEmpty()) {
            throw new UserIdNotFoundException(id);
        } else {
            userservice.deleteUser(userByIdOpt.get());
            return ResponseEntity.ok().build();
        }
    }
}
