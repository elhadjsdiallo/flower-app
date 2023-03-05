package com.example.flowerapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.flowerapp.entities.Flower;
import com.example.flowerapp.entities.User;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.FlowerModel;
import com.example.flowerapp.repository.FlowerRepository;
import com.example.flowerapp.repository.UserRepository;

@Service
@Transactional
public class FlowerServiceImpl implements IFlowerService {
    @Autowired
    private FlowerRepository flowerRepository;
    @Autowired
    private UserRepository userRepository;

    private void mapMultiPartFile(Flower flower,MultipartFile file)
    {
        flower.setImageName(file.getOriginalFilename());

    }

    @Override
    public Flower uploadFlower(FlowerModel flowerModel) throws SqlBaseException {

        try {
            Flower flower = Flower.builder()
                    .description(flowerModel.getDescription())
                    .imageName(flowerModel.getFlowerImage().getOriginalFilename())
                    .imagePath("/flower/image/")
                    .type(flowerModel.getFlowerImage().getContentType())
                    .image(flowerModel.getFlowerImage().getBytes())
                    .latitude(flowerModel.getLatitude())
                    .longitude(flowerModel.getLongitude())
                    .adresse(flowerModel.getAdresse())
                    .build();

            Optional<User> optionalUser = userRepository.findById(flowerModel.getUserId());
            if (optionalUser.isPresent()) {
                flower = flowerRepository.save(flower);
                flower.setImagePath(flower.getImagePath() + "" + flower.getId());
                User user = optionalUser.get();
                flower.setUser(user);

                user.getUserFlowers().add(flower);
                userRepository.save(user);

                return flower;

            }
            throw new SqlBaseException("user not found ");
        } catch (Exception e) {
            throw new SqlBaseException("failed to save image", e);
        }

    }

    @Override
    public List<Flower> getUserFlowers(Long userId) throws SqlBaseException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return flowerRepository.findByUser(optionalUser.get());
        }
        throw new SqlBaseException("failed to get user flowers ");

    }

    @Override
    public Flower getFlowerImage(Long imageId) throws SqlBaseException {

        Optional<Flower> flower = flowerRepository.findById(imageId);

        if (flower.isPresent()) {
            return flower.get();
        }
        throw new SqlBaseException("failed to get image by name");
    }

    @Override
    public List<Flower> getAllFlowers() {

        return flowerRepository.findAll();
    }

    @Override
    public Flower getFlowerById(Long flowerId) throws SqlBaseException {
        

      Optional<Flower>optional=flowerRepository.findById(flowerId);
      if(optional.isPresent())
      {
        return optional.get();
      }
        throw new SqlBaseException("flower not foud");
    }

    @Override
    public void deleteFlowerById(Long flowerId) throws SqlBaseException {
    
       try {
        flowerRepository.deleteById(flowerId);
        
       } catch (Exception e) {
        throw new SqlBaseException("failed to delete flower", e);
       }
      
    }
}
