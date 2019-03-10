/**
 * Extending JpaRepository gives this interface
 * the opportunity to work with the "categories" table in DB
 * successfully using JpaRepository methods
 */

package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findByName(String name);
}
