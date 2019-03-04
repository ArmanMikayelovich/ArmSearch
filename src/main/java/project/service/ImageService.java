/**
 * this class calls the methods of ImageRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.model.ItemEntity;
import project.repository.ImageRepository;

import java.io.*;
import java.nio.file.Files;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final DeletedImagesPathService deletedImagesPathService;

    public ImageService(ImageRepository imageRepository, DeletedImagesPathService deletedImagesPathService) {
        this.imageRepository = imageRepository;
        this.deletedImagesPathService = deletedImagesPathService;
    }

    @Transactional
    public ImageEntity addImage(ItemEntity itemEntity, MultipartFile imgFile, int id) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setItemEntity(itemEntity);

        String fileExtension = FilenameUtils.getExtension(imgFile.getOriginalFilename());
        String fileName = "itemImages/"+ itemEntity.getId() + "_" + id + "." + fileExtension;
        try {

            byte[] bytes = imgFile.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
            imageEntity.setFilePath(fileName);
        } catch (Exception e) {
            throw new IOException();
        }
        imageRepository.save(imageEntity);
        return imageEntity;
    }
    @Transactional
    public void deleteImage (Long imageId){
        ImageEntity imageEntity = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("ImageEntity", "id", imageId));
        File file = new File(imageEntity.getFilePath());
        imageRepository.delete(imageEntity);
        file.delete();
    }

    @RequestMapping(value = "image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imagePath) throws IOException {

        File serverFile = new File(imagePath);

        return Files.readAllBytes(serverFile.toPath());
    }

    public byte[] getBytes(String imgPath) {
        File file = new File(imgPath);
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesArray;
    }

    public void deleteAllImages(ItemEntity itemEntity) {
        deletedImagesPathService.saveDeletedImagesPathFromImageList(itemEntity.getImageEntityList());
        itemEntity.getImageEntityList().forEach(imageRepository::delete);
        imageRepository.flush();

    }

    public Long getCountOfImages() {

        return imageRepository.count();
    }
}
