package com.example.flowerapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flowerapp.entities.Flower;
import com.example.flowerapp.entities.User;
import com.example.flowerapp.exception.SqlBaseException;

public interface FlowerRepository extends JpaRepository<Flower,Long>{
    
    Optional<Flower>findByImageName(String imageName) throws SqlBaseException;
    List<Flower>findByUser(User user) throws SqlBaseException;
}
