package project.service;

import lombok.Data;
<<<<<<< HEAD
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> Arman
import org.springframework.stereotype.Service;
import project.model.DeletedImagesPath;
import project.model.Image;
import project.repository.DeletedImagesPathRepository;

<<<<<<< HEAD
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
=======
>>>>>>> Arman
import java.util.List;

@Service
@Data
public class DeletedImagesPathService {

<<<<<<< HEAD
    @Autowired
    private DeletedImagesPathRepository deletedImagesPathRepository;

    public void deletedImagesPathsaver(List<Image> images){

        for (Image img: images) {
=======
    private  final DeletedImagesPathRepository deletedImagesPathRepository;

    public void saveDeletedImagesPathFromImageList(List<Image> imageList){

        for (Image img: imageList) {
>>>>>>> Arman

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