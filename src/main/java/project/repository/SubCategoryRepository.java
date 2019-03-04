/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "subcategories" table in DB
 * successfully using JpaRepository methods
 */
package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.SubCategoryEntity;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> {

}
