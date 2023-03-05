package com.example.flowerapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flowerapp.exception.FailedToSaveUserException;
import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.ResponseToken;
import com.example.flowerapp.model.SignInRequest;
import com.example.flowerapp.model.UserModel;
import com.example.flowerapp.response.CustomApiResponse;
import com.example.flowerapp.response.CustomBaseResponse;
import com.example.flowerapp.services.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api")
@CustomBaseResponse
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/public/register")
    public CustomApiResponse register(@RequestBody @Valid UserModel userModel) throws  ServiceBaseException {

        UserModel response = userService.createUser(userModel);
        return new CustomApiResponse(HttpStatus.OK, response);

        

    }

    @PostMapping("/public/signin")
    public CustomApiResponse connectUser(@RequestBody @Valid SignInRequest signInRequest) throws ServiceBaseException {

        ResponseToken responseToken = userService.signInUser(signInRequest);
        return new CustomApiResponse(HttpStatus.OK, responseToken);
    }

    @GetMapping("/private/users/current/")
    public CustomApiResponse getCurrentUser(Principal principal) throws SqlBaseException {

        UserModel userModel = userService.getCurrentUser(principal.getName());

        return new CustomApiResponse(HttpStatus.OK, userModel);
    }

}
