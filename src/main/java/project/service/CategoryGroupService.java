package project.service;

import org.springframework.stereotype.Service;
import project.exception.ResourceNotFoundException;
import project.model.CategoryGroup;
import project.repository.CategoryGroupRepository;
@Service
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroupService(CategoryGroupRepository categoryGroupRepository) {
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public CategoryGroup findByName(String name) {
       return categoryGroupRepository.findByName(name);
    }

    public CategoryGroup findById(Integer id) {
        return categoryGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryGroup", "id", id));

    }

    public CategoryGroup createCategoryGroup(CategoryGroup categoryGroup) {
       return categoryGroupRepository.save(categoryGroup);

    }

    public void deleteCategoryGroup(Integer categoryGroupId) {
        CategoryGroup categoryGroup = categoryGroupRepository.findById(categoryGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryGroup", "id", categoryGroupId));
        categoryGroupRepository.delete(categoryGroup);
    }
}
