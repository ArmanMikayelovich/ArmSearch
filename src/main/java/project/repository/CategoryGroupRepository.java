package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.CategoryGroup;

import java.util.List;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Integer> {
    CategoryGroup findByName(String name);
}
