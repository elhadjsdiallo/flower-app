package com.example.flowerapp.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.flowerapp.exception.FailedToSaveUserException;
import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.response.CustomApiResponse;
import com.example.flowerapp.response.CustomBaseResponse;

@ControllerAdvice(annotations = CustomBaseResponse.class)
@ResponseStatus
@ResponseBody
public class ResponseExcetpionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FailedToSaveUserException.class)
    private CustomApiResponse handleFailedToSaveUser(FailedToSaveUserException failedToSaveUserException,
            WebRequest webRequest) {

        return new CustomApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, failedToSaveUserException.getMessage(),
                failedToSaveUserException.getCause().getMessage());
    }

    @ExceptionHandler(SqlBaseException.class)
    private CustomApiResponse handleSqlException(SqlBaseException sqlBaseException,
            WebRequest webRequest) {

        return new CustomApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, sqlBaseException.getMessage(),
                sqlBaseException.getCause() != null ? sqlBaseException.getCause().getMessage() : "");
    }
    @ExceptionHandler(ServiceBaseException.class)
    private CustomApiResponse handleServiceException(ServiceBaseException serviceBaseException,
            WebRequest webRequest) {

        return new CustomApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, serviceBaseException.getMessage(),
        serviceBaseException.getCause() != null ? serviceBaseException.getCause().getMessage() : "");
    }

    @ExceptionHandler(AuthenticationException.class)
    private CustomApiResponse handlejwtAuthentificationExcetpiont(Exception jwtException, WebRequest webRequest) {
        return new CustomApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, jwtException.getCause().getMessage());

    }
    @ExceptionHandler(IOException.class)
    private CustomApiResponse handleIOException(IOException ioException, WebRequest webRequest) {
        return new CustomApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ioException.getCause().getMessage());

    }

}
