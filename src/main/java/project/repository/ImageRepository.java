/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "images" table in DB
 * successfully using JpaRepository methods
 */

package project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {


}

