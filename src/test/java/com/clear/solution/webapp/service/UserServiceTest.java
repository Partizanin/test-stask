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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final LocalDate USER_BIRTHDAY = LocalDate.now();
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private AutoCloseable autoCloseable;

    private MockedStatic<Patcher> patcherMockedStatic;

    private static final User USER = new User(1L, LocalDate.of(2000, 1, 1));
    private static final String USER_PHONE_NUMBER = "USER_PHONE_NUMBER";
    private static final String USER_ADDRESS = "USER_ADDRESS";
    private static final String USER_LAST_NAME = "USER_LAST_NAME";
    private static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    private static final String USER_EMAIL = "user@mail.com";

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository);
        this.patcherMockedStatic = mockStatic(Patcher.class);
        ReflectionTestUtils.setField(userService, "MINIMUM_YEARS_REQUIRED", 18);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        patcherMockedStatic.close();
    }

    @Test
    void findAll() {
        ResponseEntity<List<User>> response = userService.findAll();
        verify(userRepository).findAll();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    void findById() {
        given(userRepository.findById(USER.getId())).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findById(USER.getId());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(argumentCaptor.capture());
        assertEquals(USER.getId(), argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByIdWithException() {
        assertThrows(UserIdNotFoundException.class, () -> userService.findById(USER.getId()));
    }

    @Test
    void findByEmail() {
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByEmail(USER_EMAIL);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByEmail(argumentCaptor.capture());
        assertEquals(USER_EMAIL, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByEmailWithException() {
        assertThrows(UserEmailNotFoundException.class, () -> userService.findByEmail(USER_EMAIL));
    }

    @Test
    void findByFirstName() {
        given(userRepository.findByFirstName(USER_FIRST_NAME)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByFirstName(USER_FIRST_NAME);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByFirstName(argumentCaptor.capture());
        assertEquals(USER_FIRST_NAME, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByFirstNameWithException() {
        assertThrows(UserFirstNameNotFoundException.class, () -> userService.findByFirstName(USER_FIRST_NAME));
    }

    @Test
    void findByLastName() {
        given(userRepository.findByLastName(USER_LAST_NAME)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByLastName(USER_LAST_NAME);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByLastName(argumentCaptor.capture());
        assertEquals(USER_LAST_NAME, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByLastNameWithException() {
        assertThrows(UserLastNameNotFoundException.class, () -> userService.findByLastName(USER_LAST_NAME));
    }

    @Test
    void findByAddress() {
        given(userRepository.findByAddress(USER_ADDRESS)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByAddress(USER_ADDRESS);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByAddress(argumentCaptor.capture());
        assertEquals(USER_ADDRESS, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByAddressWithException() {
        assertThrows(UserAddressNotFoundException.class, () -> userService.findByAddress(USER_ADDRESS));
    }

    @Test
    void findByPhoneNumber() {
        given(userRepository.findByPhoneNumber(USER_PHONE_NUMBER)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByPhoneNumber(USER_PHONE_NUMBER);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByPhoneNumber(argumentCaptor.capture());
        assertEquals(USER_PHONE_NUMBER, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByPhoneNumberWithException() {
        assertThrows(UserPhoneNumberNotFoundException.class, () -> userService.findByPhoneNumber(USER_PHONE_NUMBER));
    }


    @Test
    void findByBirthDate() {
        given(userRepository.findByBirthDate(USER_BIRTHDAY)).willReturn(Optional.of(USER));
        ResponseEntity<User> response = userService.findByBirthDate(USER_BIRTHDAY);
        ArgumentCaptor<LocalDate> argumentCaptor = ArgumentCaptor.forClass(LocalDate.class);
        verify(userRepository).findByBirthDate(argumentCaptor.capture());
        assertEquals(USER_BIRTHDAY, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByBirthDateWithException() {
        assertThrows(UserBirthDateNotFoundException.class, () -> userService.findByBirthDate(USER_BIRTHDAY));
    }

    @Test
    void findByBirthDateRange() {
        given(userRepository.findByBirthDateBetween(USER_BIRTHDAY, USER_BIRTHDAY)).willReturn(Optional.of(List.of(USER)));
        ResponseEntity<List<User>> response = userService.findByBirthDateRange(USER_BIRTHDAY, USER_BIRTHDAY);
        ArgumentCaptor<LocalDate> argumentCaptor = ArgumentCaptor.forClass(LocalDate.class);
        verify(userRepository).findByBirthDateBetween(argumentCaptor.capture(), argumentCaptor.capture());
        assertEquals(USER_BIRTHDAY, argumentCaptor.getValue());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByBirthDateRangeWithException() {
        assertThrows(UserBirthDateRangeNotFoundException.class, () ->
                userService.findByBirthDateRange(USER_BIRTHDAY, USER_BIRTHDAY));
    }

    @Test
    void findByBirthDateRangeBadRequest() {
        ResponseEntity<List<User>> response =
                userService.findByBirthDateRange(USER_BIRTHDAY.plusDays(1), USER_BIRTHDAY);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateUser() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(USER));
        given(userRepository.save(any(User.class))).willReturn(USER);
        ResponseEntity<User> response = userService.updateUser(USER.getId(), USER);
        ArgumentCaptor<User> argumentCaptorUser = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Long> argumentCaptorUserId = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(argumentCaptorUserId.capture());
        verify(userRepository).save(argumentCaptorUser.capture());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateUserWithException() {
        assertThrows(UserIdNotFoundException.class, () -> userService.updateUser(USER.getId(), USER));
    }

    @Test
    void patchUser() {
        User user = new User(1L, LocalDate.of(2000, 1, 1));
        user.setId(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(USER));
        given(userRepository.save(USER)).willReturn(any(User.class));
        ResponseEntity<User> response = userService.patchUser(user, user.getId());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        patcherMockedStatic.verify(() -> Patcher.userPatcher(any(User.class), any(User.class)));
        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void patchUserWithException() {
        assertThrows(UserIdNotFoundException.class, () -> userService.patchUser(new User(1L, LocalDate.of(2000, 1, 1)), 1L));
    }

    @Test
    void deleteUser() {
        User user = new User(1L, LocalDate.of(2000, 1, 1));
        user.setId(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        ResponseEntity<User> response = userService.deleteUser(USER.getId());
        ArgumentCaptor<User> argumentCaptorUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).delete(argumentCaptorUser.capture());
        assertEquals(USER.getId(), argumentCaptorUser.getValue().getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteUserWithException() {
        assertThrows(UserIdNotFoundException.class, () -> userService.deleteUser(USER.getId()));
    }


    @Test
    void createUser() {
        ResponseEntity<User> response = userService.createUser(USER);
        ArgumentCaptor<User> argumentCaptorUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptorUser.capture());
        assertEquals(USER.getId(), argumentCaptorUser.getValue().getId());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void createUserWithException() {
        ResponseEntity<User> response = userService.createUser(new User(1L, LocalDate.now()));
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}