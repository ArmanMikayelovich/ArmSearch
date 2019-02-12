package project.service;

import project.model.Category;
import project.model.CategoryGroup;
import project.repository.CategoryRepository;

import java.util.Optional;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);


    }

    public Category createCategory() {
        CategoryGroup categoryGroup = categoryGroupService.findById(categoryDto.getGroupId());

        Category category = new Category(categoryDto.getName(), categoryGroup);
    }
}
