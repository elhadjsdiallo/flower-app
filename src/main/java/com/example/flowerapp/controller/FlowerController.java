package com.example.flowerapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.flowerapp.entities.Flower;
import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.exception.SqlBaseException;
import com.example.flowerapp.model.FlowerModel;
import com.example.flowerapp.response.CustomApiResponse;
import com.example.flowerapp.response.CustomBaseResponse;
import com.example.flowerapp.services.IFlowerService;

@RestController
@RequestMapping("/rest/api")
@CustomBaseResponse
public class FlowerController {
    @Autowired
    private IFlowerService flowerService;

    @PostMapping("private/flower/image/user/{userId}")
    public CustomApiResponse addFlower(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "image") MultipartFile image,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "adresse") String adresse

    ) throws SqlBaseException {

        FlowerModel flowerModel = FlowerModel.builder()
                .description(description)
                .userId(userId)
                .flowerImage(image)
                .longitude(longitude)
                .adresse(adresse)
                .latitude(latitude)

                .build();
        Flower flower = flowerService.uploadFlower(flowerModel);
        return new CustomApiResponse(HttpStatus.OK, flower);
    }

    @GetMapping("private/flower/image/user/{userId}")
    public CustomApiResponse getUserFlower(@PathVariable(name = "userId") Long userId) throws SqlBaseException {

        List<Flower> userFlower = flowerService.getUserFlowers(userId);

        return new CustomApiResponse(HttpStatus.OK, userFlower);

    }

    @GetMapping("private/flowers")
    public CustomApiResponse getAllFlowers() throws SqlBaseException {

        List<Flower> userFlower = flowerService.getAllFlowers();

        return new CustomApiResponse(HttpStatus.OK, userFlower);

    }
    

    @GetMapping("private/flower/{flowerId}")
    public CustomApiResponse getFlower(@PathVariable(name = "flowerId") Long flowerId) throws SqlBaseException {

        Flower userFlower = flowerService.getFlowerById(flowerId);

        return new CustomApiResponse(HttpStatus.OK, userFlower);

    }
    @DeleteMapping("private/flower/{flowerId}")
    public CustomApiResponse deleteFlower(@PathVariable(name = "flowerId") Long flowerId) throws SqlBaseException {

         flowerService.deleteFlowerById(flowerId);

        return new CustomApiResponse(HttpStatus.OK,"");

    }

    @GetMapping("public/flower/image/{imageId}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Long imageId)
            throws SqlBaseException, ServiceBaseException, IOException {

        Flower flower = flowerService.getFlowerImage(imageId);
        ByteArrayResource resource = new ByteArrayResource(flower.getImage());
        return ResponseEntity.ok().header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachement; filename=\"" + flower.getImageName() + "\"")
                .body(resource);

    }

}
