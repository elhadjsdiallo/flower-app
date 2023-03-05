package com.example.flowerapp.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flowerapp.entities.Role;
import com.example.flowerapp.entities.User;
import com.example.flowerapp.exception.FailedToSaveUserException;
import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.ResponseToken;
import com.example.flowerapp.model.SignInRequest;
import com.example.flowerapp.model.UserModel;
import com.example.flowerapp.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    private JwtTokenService jwtTokenService;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    /**
     * @param userRepository
     * @param jwtTokenService
     * @param authenticationManager
     * @param passwordEncoder
     */
    public UserServiceImpl(UserRepository userRepository, JwtTokenService jwtTokenService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel createUser(UserModel userModel) throws ServiceBaseException {

        User user = User.builder()
                .email(userModel.getEmail())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .role(Role.USER)
                .enabled(true)
                .build();
        try {
            user = userRepository.save(user);
            return UserModel.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();

        } catch (Exception e) {

            throw new  ServiceBaseException("user already exist", e);
        }

    }

    @Override
    public ResponseToken signInUser(SignInRequest signInRequest) throws ServiceBaseException {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

            String token = jwtTokenService.generateToken(authentication);

            return new ResponseToken(token);

        } catch (Exception e) {

            throw new ServiceBaseException("authentication failed", e);
        }

    }

    @Override
    public UserModel getCurrentUser(String userName) throws SqlBaseException {

        Optional<User> optional = userRepository.findByEmail(userName);
        if (optional.isPresent()) {
            User user = optional.get();
            return UserModel.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();

        }

        throw new SqlBaseException("failed to get current user");
    }

}
