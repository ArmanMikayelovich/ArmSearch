package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.CategoryGroupEntity;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroupEntity, Integer> {
}
