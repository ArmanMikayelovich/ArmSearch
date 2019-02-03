package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import project.exception.ResourceNotFoundException;
import project.model.CategoryGroupEntity;
import project.repository.CategoryGroupRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryGroupController {
    @Autowired
    CategoryGroupRepository categoryGroupRepository;

    // Get All CategoryGroups
    @GetMapping("/categoryGroups")
    public List<CategoryGroupEntity> getAllCategoryGroups() {

        return categoryGroupRepository.findAll();
    }

    // Create a new CategoryGroup //TODO this must be accesible only for admins and delete
    @PostMapping("/categoryGroups")
    public CategoryGroupEntity createCategoryGroup(@Valid @RequestBody CategoryGroupEntity categoryGroupEntity) {
        return categoryGroupRepository.save(categoryGroupEntity);
    }

    // Get a Single categoryGroup
    @GetMapping("/categoryGroups/{id}")
    public CategoryGroupEntity getCategoryGroupById(@PathVariable(value = "id") Integer categoryGroupId) {
        return categoryGroupRepository.findById(categoryGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryGroup", "id", categoryGroupId));
    }

    // Delete a CategoryGroup
    @DeleteMapping("/categoryGroups/{id}")
    public ResponseEntity<?> deleteCategoryGroup(@PathVariable(value = "id") Integer categoryGroupId) {
        CategoryGroupEntity categoryGroup = categoryGroupRepository.findById(categoryGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryGroup", "id", categoryGroupId));
        categoryGroupRepository.delete(categoryGroup);

        return ResponseEntity.ok().build();
    }
}
