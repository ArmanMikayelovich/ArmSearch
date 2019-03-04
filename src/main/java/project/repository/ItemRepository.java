/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "items" table in DB
 * successfully using JpaRepository methods
 */

package project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.model.ItemEntity;
import project.model.SubCategoryEntity;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    //search with pagination
    Page<ItemEntity> findAllBySubCategoryEntity(SubCategoryEntity subCategoryEntity, Pageable pageable);

    //search with nativ query
    @Query(value = "select * from items order by rand() limit 12", nativeQuery = true)
    List<ItemEntity> getRandomItems();

    //search with pagination using query ignored case
    @Query("SELECT i FROM ItemEntity i WHERE " +
            "LOWER(i.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<ItemEntity> findAllByTitleOrDescription(@Param("searchTerm") String searchTerm, Pageable pageable);

}

