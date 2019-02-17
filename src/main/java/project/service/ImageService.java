package project.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.exception.ResourceNotFoundException;
import project.model.Image;
import project.model.Item;
import project.repository.ImageRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    @Transactional
    public Image addImage(Item item, MultipartFile imgFile,int id) throws IOException {
        Image image = new Image();
        image.setItem(item);

        String fileExtension = FilenameUtils.getExtension(imgFile.getOriginalFilename());
        String fileName = "itemImages/"+ item.getId() + "_" + id + "." + fileExtension;
        try {

            byte[] bytes = imgFile.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
            image.setFilePath(fileName);
        } catch (Exception e) {
            throw new IOException();
        }
        imageRepository.save(image);
        return image;
    }
    @Transactional
    public void deleteImage (Long imageId){
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        File file = new File(image.getFilePath());
        imageRepository.delete(image);
        file.delete();
    }

    @RequestMapping(value = "image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imagePath) throws IOException {

        File serverFile = new File(imagePath);

        return Files.readAllBytes(serverFile.toPath());
    }
}
