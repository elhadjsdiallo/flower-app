package com.example.flowerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class SignInRequest {

    private String email;
    private String password;
    
}
