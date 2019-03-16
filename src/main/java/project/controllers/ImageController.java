package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.repository.ImageRepository;
import project.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

//TODO ANI ARMAN nayel telegrami grac@

@RestController
@RequestMapping("/items")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @RequestMapping(
            value = "/itemImages",
            produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET
    )
    public @ResponseBody
    ResponseEntity<byte[]> getImageWithMediaType(@RequestParam(value="imageEntity") String imagePath, HttpServletResponse response) throws IOException {


        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageService.getBytes(imagePath));
    }


    // Delete an ImageEntity
    @DeleteMapping("/static/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        ImageEntity imageEntity = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("ImageEntity", "id", imageId));
        File file = new File(imageEntity.getFilePath());//
        imageRepository.delete(imageEntity);            // TODO Arman check
        file.delete();                            //
        return ResponseEntity.ok().build();
    }
}
