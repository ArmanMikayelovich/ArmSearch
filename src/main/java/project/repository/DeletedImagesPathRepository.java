/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "deleted_images_paths" table in DB
 * successfully using JpaRepository methods
 */

package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.model.DeletedImagesPathEntity;

public interface DeletedImagesPathRepository extends JpaRepository<DeletedImagesPathEntity, Long>{

}