package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.repository.ImageRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;


    // Create/Upload a new Image
    @PostMapping("/images")
    public ImageEntity createImage(@Valid @RequestBody ImageEntity imageEntity) {
        return imageRepository.save(imageEntity);
    }

    // Get a Single Product // TODO ete mez petq lini mek producti bolor imagener@ berel apa es metod@ miqani angam kkanchvi
    @GetMapping("/images/{id}")
    public ImageEntity getImageById(@PathVariable(value = "id") Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
    }


    // Delete an Image
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        imageRepository.delete(image);

        return ResponseEntity.ok().build();
    }
}
