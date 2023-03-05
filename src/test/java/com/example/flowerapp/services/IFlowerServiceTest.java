package com.example.flowerapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.flowerapp.entities.Flower;
import com.example.flowerapp.entities.Role;
import com.example.flowerapp.entities.User;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.FlowerModel;
import com.example.flowerapp.model.UserModel;
import com.example.flowerapp.repository.FlowerRepository;
import com.example.flowerapp.repository.UserRepository;

@SpringBootTest
class IFlowerServiceTest {

    @Autowired
    private IFlowerService serviceFlower;

    @MockBean
    private FlowerRepository flowerRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder testPasswordEncoder;
    private User givenUser;
    private UserModel givenUserModel;

    private FlowerModel givenFlowerModel;
    private MockMultipartFile imagFile;

    @BeforeEach
    void setup() {

        imagFile = new MockMultipartFile(
                "fleur",
                "fleur.png",
                "image/png",
                "image content ****************".getBytes());
        givenFlowerModel = FlowerModel.builder()
                .userId(1L)
                .description("fleur du brésil")
                .adresse("22 rue paris sud")
                .latitude(46.00)
                .longitude(3.00)
                .build();

        givenUser = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .password(testPasswordEncoder.encode("userpass"))
                .firstName("username")
                .lastName("userLastName")
                .role(Role.USER)
                .enabled(true)
                .build();
        givenUser.setUserFlowers(new ArrayList<Flower>());
        givenUserModel = UserModel.builder()
                .id(1L)
                .email("user@gmail.com")
                .firstName("username")
                .lastName("userLastName")

                .build();

    }

    @Test
    @DisplayName("Testing upload flower")
    void testUploadFlower() throws IOException, SqlBaseException {

        // Given
        Flower flower = Flower.builder()
                .description(givenFlowerModel.getDescription())
                .imageName(imagFile.getOriginalFilename())
                .imagePath("/flower/image/")
                .type(imagFile.getContentType())
                .image(imagFile.getBytes())
                .latitude(givenFlowerModel.getLatitude())
                .longitude(givenFlowerModel.getLongitude())
                .adresse(givenFlowerModel.getAdresse())
                .build();
        Flower expectedFlower = Flower.builder()
                .id(1L)
                .description(givenFlowerModel.getDescription())
                .imageName(imagFile.getOriginalFilename())
                .imagePath("/flower/image/1")
                .type(imagFile.getContentType())
                .image(imagFile.getBytes())
                .latitude(givenFlowerModel.getLatitude())
                .longitude(givenFlowerModel.getLongitude())
                .adresse(givenFlowerModel.getAdresse())
                .build();
        List<Flower> flowers = new ArrayList<>();
        flowers.add(expectedFlower);
        User finalUser = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .password(testPasswordEncoder.encode("userpass"))
                .firstName("username")
                .lastName("userLastName")
                .role(Role.USER)
                .enabled(true)
                .userFlowers(flowers)
                .build();
        finalUser.getUserFlowers().add(expectedFlower);

        // WHEN
        givenFlowerModel.setFlowerImage(imagFile);
     

        when(userRepository.findById(givenUserModel.getId())).thenReturn(Optional.of(givenUser));
        when(flowerRepository.save(flower)).thenReturn(expectedFlower);
        when(userRepository.save(finalUser)).thenReturn(finalUser);
        Flower insertedFlower = serviceFlower.uploadFlower(givenFlowerModel);
        // THEN

        assertEquals(expectedFlower.getId(), insertedFlower.getId());
        assertEquals(expectedFlower.getDescription(), insertedFlower.getDescription());
        assertEquals(expectedFlower.getImageName(), insertedFlower.getImageName());

    }

    @Test
    void testGetAllFlowers() {

    }

    @Test
    void testGetFlowerById() {

    }

    @Test
    void testGetFlowerImage() {

    }

    @Test
    void testGetUserFlowers() {

    }

}
