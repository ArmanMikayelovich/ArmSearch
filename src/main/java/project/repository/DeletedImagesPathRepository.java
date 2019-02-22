package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.DeletedImagesPath;

public interface DeletedImagesPathRepository extends JpaRepository<DeletedImagesPath, Long>{

}