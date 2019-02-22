package project.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import project.model.DeletedImagesPath;
import project.model.Image;
import project.repository.DeletedImagesPathRepository;

import java.util.List;

@Service
@Data
public class DeletedImagesPathService {

    private  final DeletedImagesPathRepository deletedImagesPathRepository;

    public void saveDeletedImagesPathFromImageList(List<Image> imageList){

        for (Image img: imageList) {

            DeletedImagesPath DIP = new DeletedImagesPath();
            DIP.setFilePath(img.getFilePath());
            deletedImagesPathRepository.save(DIP);
        }
    }


    public List<DeletedImagesPath> findAllDeletedImagesPaths() {
        return deletedImagesPathRepository.findAll();

    }

    public void truncate(){

        deletedImagesPathRepository.deleteAll();
    }

}