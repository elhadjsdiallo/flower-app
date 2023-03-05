package com.example.flowerapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import com.example.flowerapp.entities.Role;
import com.example.flowerapp.entities.User;
import com.example.flowerapp.exception.FailedToSaveUserException;
import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.ResponseToken;
import com.example.flowerapp.model.SignInRequest;
import com.example.flowerapp.model.UserModel;
import com.example.flowerapp.repository.UserRepository;

@SpringBootTest
@ContextConfiguration
class IUserServiceTest {

    private UserModel givenUserModel;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;
    @MockBean
    private PasswordEncoder testPasswordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup() {

        givenUserModel = UserModel.builder()
                .firstName("Oumar")
                .lastName("Barry")
                .email("oumar@gmail.com")
                .password("oumarpass")
                .build();

    }

    @Test
    @DisplayName("Testing creating a user")
    void testUserShoudlBeCreated() throws FailedToSaveUserException, ServiceBaseException {

        // GIVEN
        String encodedUserPass = new BCryptPasswordEncoder().encode(givenUserModel.getPassword());
        User user1 = User.builder()
                .email(givenUserModel.getEmail())
                .password(encodedUserPass)
                .firstName(givenUserModel.getFirstName())
                .lastName(givenUserModel.getLastName())
                .role(Role.USER)
                .enabled(true)
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .email(givenUserModel.getEmail())
                .password(user1.getPassword())
                .firstName(givenUserModel.getFirstName())
                .lastName(givenUserModel.getLastName())
                .role(Role.USER)
                .enabled(true)
                .build();
        // WHEN
        when(testPasswordEncoder.encode(givenUserModel.getPassword())).thenReturn(encodedUserPass);
        when(userRepository.save(user1)).thenReturn(expectedUser);

        UserModel createUserModel = userService.createUser(givenUserModel);
        // THEN
        assertEquals(expectedUser.getId(), createUserModel.getId());
        assertEquals(expectedUser.getEmail(), createUserModel.getEmail());
        assertEquals(expectedUser.getFirstName(), createUserModel.getFirstName());
        assertEquals(expectedUser.getLastName(), createUserModel.getLastName());

    }

    @Test
    @WithMockUser("user1")
    @DisplayName("Test if user is able to signIn")
    void testWhenUserSignIn() throws ServiceBaseException {

        // GIVEN
        SignInRequest signInRequest = new SignInRequest("user1", "password");
        SignInRequest wrongSignInRequest = new SignInRequest("user", "password");

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        // WHEN
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword())))
                .thenReturn(authentication);

        ResponseToken responseToken = userService.signInUser(signInRequest);
        Exception exception = assertThrows(ServiceBaseException.class, () -> {
            userService.signInUser(wrongSignInRequest);
        });

        // THEN

        assertNotNull(responseToken.getToken());
        assertEquals("authentication failed", exception.getMessage());

    }

    @Test
    @DisplayName("Test getting user informations after authentication is complete")
    void testgetUserByEmail() throws SqlBaseException {
        // GIVEN

        String email = "oumar@gmail.com";
        String notFoundEmail = "wrongEmail@gmail.com";

        String encodedUserPass = new BCryptPasswordEncoder().encode(givenUserModel.getPassword());
        User user1 = User.builder()
                .id(1L)
                .email(givenUserModel.getEmail())
                .password(encodedUserPass)
                .firstName(givenUserModel.getFirstName())
                .lastName(givenUserModel.getLastName())
                .role(Role.USER)
                .enabled(true)
                .build();
        // WHEN
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user1));

        UserModel expectedUserModel = userService.getCurrentUser(email);
        Exception userNotFoundException = assertThrows(SqlBaseException.class,
                () -> {
                    userService.getCurrentUser(notFoundEmail);
                });

        // THEN
        assertEquals(email, expectedUserModel.getEmail());
        assertEquals("failed to get current user", userNotFoundException.getMessage());

    }

}
