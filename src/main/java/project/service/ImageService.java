package project.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import project.model.Image;
import project.model.Item;
import project.repository.ImageRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image addImage(Item item, MultipartFile imgFile,int id) throws IOException {
        Image image = new Image();
        image.setItem(item);

        String fileExtension = FilenameUtils.getExtension(imgFile.getOriginalFilename());
        String fileName = "img/" + item.getId() + "_" + id + "." + fileExtension;
        try {

            byte[] bytes = imgFile.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            throw new IOException();
        }
        imageRepository.save(image);
        return image;
    }
}
