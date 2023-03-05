package com.example.flowerapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.flowerapp.model.ResponseToken;
import com.example.flowerapp.model.SignInRequest;
import com.example.flowerapp.model.UserModel;
import com.example.flowerapp.response.CustomApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)

class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    private UserModel givenUserModel;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        // GIVEN
        givenUserModel = UserModel.builder()
                .firstName("Oumar")
                .lastName("Barry")
                .email("oumar@gmail.com")
                .password("oumarpass")
                .build();
        objectMapper = new ObjectMapper();

    }

    @Test
    @DisplayName("Testing user first registration")
    @Order(1)
    void testWhenUserRegister() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());

        // Given
        String body = objectMapper.writeValueAsString(givenUserModel);
        mockMvc.perform(post("/rest/api/public/register").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..firstName").value("Oumar"))
                .andExpect(jsonPath("$..email").value("oumar@gmail.com"));

        mockMvc.perform(post("/rest/api/public/register").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$..error").value("user already exist"))
                .andReturn();

    }

    @Test
    @DisplayName("Test when user signin with userName and password")
    @Order(2)
    void testWhenUserSignIn() throws Exception {
        SignInRequest signInRequest = new SignInRequest(givenUserModel.getEmail(), givenUserModel.getPassword());

        String body = objectMapper.writeValueAsString(signInRequest);
        mockMvc.perform(post("/rest/api/public/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..token").isNotEmpty());

    }

    @Test
    @DisplayName("Test accessing secured urls")
    @Order(3)
    void testWhenAccessPrivateLink() throws Exception {

        SignInRequest signInRequest = new SignInRequest(givenUserModel.getEmail(), givenUserModel.getPassword());

        String body = objectMapper.writeValueAsString(signInRequest);
        MvcResult mvcResult = mockMvc.perform(post("/rest/api/public/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        objectMapper.registerModule(new JavaTimeModule());

        CustomApiResponse customApiResponse = objectMapper.readValue(responseBody, CustomApiResponse.class);
        ResponseToken responseToken = objectMapper
                .readValue(objectMapper.writeValueAsString(customApiResponse.getData()), ResponseToken.class);

        mockMvc.perform(get("/rest/api/private/users/current/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + responseToken.getToken()))
                .andExpect(status().isOk());
        String emptyBearer="";
        mockMvc.perform(get("/rest/api/private/users/current/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + emptyBearer))
                .andExpect(status().isUnauthorized());
        

    }

}
