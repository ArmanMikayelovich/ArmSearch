package project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Item;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query("SELECT i FROM Item i WHERE " +
            "LOWER(i.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Set<Item> findAllByTitleOrDescription(@Param("searchTerm") String searchTerm);
}

