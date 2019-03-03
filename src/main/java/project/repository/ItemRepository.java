package project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Category;
import project.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByCategory(Category category, Pageable pageable);
    @Query(value = "select * from items order by rand() limit 12", nativeQuery = true)
    List<Item> getRandomItems();
    @Query("SELECT i FROM Item i WHERE " +
            "LOWER(i.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Item> findAllByTitleOrDescription(@Param("searchTerm") String searchTerm, Pageable pageable);

}

