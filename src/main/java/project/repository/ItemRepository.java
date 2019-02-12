package project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import project.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select s from Item s where s.searchTag like ?1")
    static List<Item> findBySearchTag(String searchTag){
        return null;
    }
}

