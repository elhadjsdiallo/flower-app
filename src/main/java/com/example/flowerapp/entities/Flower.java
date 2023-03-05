package com.example.flowerapp.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "flowers",schema = "public")
public class Flower implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "imageName")
    private String imageName;
    @Column(name = "image_url")
    private String imagePath;
    @Column(name = "description")
    private String description;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "type")
    private String type;
    @Column(name = "adresse")
    private String adresse;
    @JsonIgnore
    @Column(name = "image_byte",length =999999999)
    @Lob
     private byte[] image;

    @JsonManagedReference
    @JsonIncludeProperties({"firstName","lastName"})
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
