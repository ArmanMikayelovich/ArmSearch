package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.model.Image;
import project.model.DeletedImagesPath;

import java.util.List;

public interface DeletedImagesPathRepository extends JpaRepository<DeletedImagesPath, Long>{

}