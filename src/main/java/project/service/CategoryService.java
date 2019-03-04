/**
 * this class calls the methods of CategoryRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import org.springframework.stereotype.Service;
import project.exception.ResourceNotFoundException;
import project.model.CategoryEntity;
import project.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity findByName(String name) {
       return categoryRepository.findByName(name);
    }

    public CategoryEntity findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryEntity", "id", id));

    }

    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
       return categoryRepository.save(categoryEntity);
    }

    public void deleteCategory(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryEntity", "id", categoryId));
        categoryRepository.delete(categoryEntity);
    }
}
