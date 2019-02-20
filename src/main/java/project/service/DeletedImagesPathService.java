package project.service;

import lombok.Data;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.DeletedImagesPath;
import project.model.Image;
import project.repository.DeletedImagesPathRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class DeletedImagesPathService {

    @Autowired
    private DeletedImagesPathRepository deletedImagesPathRepository;

    public void deletedImagesPathsaver(List<Image> images){

        for (Image img: images) {

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