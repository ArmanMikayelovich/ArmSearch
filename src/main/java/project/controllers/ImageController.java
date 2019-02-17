package project.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.exception.ResourceNotFoundException;
import project.model.Image;
import project.repository.ImageRepository;

import javax.validation.Valid;
import java.io.*;

//TODO ANI ARMAN nayel telegrami grac@

@RestController
@RequestMapping("/items")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;


    @RequestMapping(
            value = "/itemImages/{image}",
            produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable(value="image") String imagePath ) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("itemImages/"+imagePath);
        return IOUtils.toByteArray(in);//TODO CONTINUE
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
