package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.CategoryGroupEntity;


import java.util.List;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroupEntity, Integer> {
    List<CategoryGroupEntity> findByName(String name);

}

