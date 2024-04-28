package com.clear.solution.webapp.repository;

import com.clear.solution.webapp.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    Optional<User> findByFirstName(String firstName);

    Optional<User> findByLastName(String lastName);

    Optional<User> findByAddress(String address);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByBirthDate(LocalDate birthDate);

    Optional<List<User>> findByBirthDateBetween(LocalDate from, LocalDate to);

}
