/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "subcategories" table in DB
 * successfully using JpaRepository methods
 */
package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.SubCategoryEntity;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> {
    @Override
    @Modifying
    @Query(value = "delete from sub_categories where id = :subCategoryId", nativeQuery = true)
    void deleteById(@Param("subCategoryId") Integer subCategoryId);
}
