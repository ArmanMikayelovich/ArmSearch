package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.CategoryDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.CategoryGroup;
import project.repository.CategoryRepository;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryGroupService categoryGroupService;

    public CategoryService(CategoryRepository categoryRepository, CategoryGroupService categoryGroupService) {
        this.categoryRepository = categoryRepository;
        this.categoryGroupService = categoryGroupService;
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }
    @Transactional
    public Category createCategory(CategoryDto categoryDto) {
        CategoryGroup categoryGroup = categoryGroupService.findById(categoryDto.getGroupId());
        //TODO ANi get category group id from web

        Category category = new Category(categoryDto.getName(), categoryGroup);
        return category;
    }
    @Transactional
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }


}
