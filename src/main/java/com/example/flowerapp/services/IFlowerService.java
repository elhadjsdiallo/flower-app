package com.example.flowerapp.services;

import java.util.List;

import com.example.flowerapp.entities.Flower;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.FlowerModel;

public interface IFlowerService {

    Flower uploadFlower(FlowerModel flowerModel)
            throws SqlBaseException;
    Flower getFlowerImage(Long imageId)
            throws SqlBaseException;

    List<Flower>getUserFlowers(Long userId) throws SqlBaseException;
    List<Flower> getAllFlowers() throws SqlBaseException ;
    Flower getFlowerById(Long flowerId) throws SqlBaseException;
    void deleteFlowerById(Long flowerId)throws SqlBaseException;
}
