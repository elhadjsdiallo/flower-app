package com.example.flowerapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserModel {

    
    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
