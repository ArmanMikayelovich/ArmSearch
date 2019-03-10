/**
 * this class calls the methods of DeletedImagePathRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import project.model.DeletedImagesPathEntity;
import project.model.ImageEntity;
import project.repository.DeletedImagesPathRepository;

import java.util.List;

@Service
@Data
public class DeletedImagesPathService {

    private  final DeletedImagesPathRepository deletedImagesPathRepository;

    public void saveDeletedImagesPathFromImageList(List<ImageEntity> imageEntityList){

        for (ImageEntity img: imageEntityList) {

            DeletedImagesPathEntity DIP = new DeletedImagesPathEntity();
            DIP.setFilePath(img.getFilePath());
            deletedImagesPathRepository.save(DIP);
        }
    }


    public List<DeletedImagesPathEntity> findAllDeletedImagesPaths() {
        return deletedImagesPathRepository.findAll();

    }

    public void truncate(){

        deletedImagesPathRepository.deleteAll();
    }

}