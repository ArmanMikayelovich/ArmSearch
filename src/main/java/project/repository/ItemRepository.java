package project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

import project.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{


    @Query("select i from Item i where title like %?1% or description like %?1%")
    Page<Item> findByTitleOrByDescription(String text, Pageable pageable);
}

