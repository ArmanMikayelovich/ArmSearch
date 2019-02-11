package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;



import project.exception.ResourceNotFoundException;

import project.model.Image;

import project.repository.ImageRepository;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//TODO ANI ARMAN nayel telegrami grac@

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;


    // Create/Upload a new Image
    @PostMapping("/images")
    public Image createImage(@Valid @RequestBody Image image) {
        return imageRepository.save(image);
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
    @GetMapping("/images/{id}")
    public Image getImageById(@PathVariable(value = "id") Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
    }


    // Delete an Image
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        File file = new File(image.getFilePath());//
        imageRepository.delete(image);            // TODO Arman check
        file.delete();                            //
        return ResponseEntity.ok().build();
    }
}
