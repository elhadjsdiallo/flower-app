package com.example.flowerapp.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlowerModel {
    

    private Long id;

    private Long userId;

    private MultipartFile flowerImage;

    private String description;

    private Double longitude;

    private Double latitude;

    private String adresse;
}
