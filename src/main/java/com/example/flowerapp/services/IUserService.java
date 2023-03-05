package com.example.flowerapp.services;

import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.ResponseToken;
import com.example.flowerapp.model.SignInRequest;
import com.example.flowerapp.model.UserModel;

public interface IUserService {

    UserModel createUser(UserModel userModel) throws ServiceBaseException;

    ResponseToken signInUser(SignInRequest signInRequest) throws ServiceBaseException;

    UserModel getCurrentUser(String userName) throws SqlBaseException ;

}
