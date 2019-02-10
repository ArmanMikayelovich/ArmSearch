package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.repository.ImageRepository;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


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
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                return file.getOriginalFilename();
            } catch (Exception e) {
                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getName() + " потому что файл пустой.";
        }
    }

    // Get a Single Product // TODO ete mez petq lini mek producti bolor imagener@ berel apa es metod@ miqani angam kkanchvi
    //                          TODO CHKA TENC BAN...

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
