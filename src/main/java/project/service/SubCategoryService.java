/**
 * this class calls the methods of SubCategoryRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import project.dto.CategoryDto;
import project.exception.ResourceNotFoundException;

import project.model.CategoryEntity;
import project.model.ImageEntity;
import project.model.ItemEntity;
import project.model.SubCategoryEntity;

import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.SubCategoryRepository;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final UserService userService;

    @Autowired
    private  ItemService itemService;

    @Autowired
    DeletedImagesPathService deletedImagesPathService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ItemRepository itemRepository;

    private final CategoryService categoryService;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, UserService userService,
                              ItemService itemService, CategoryService categoryService) {
        this.subCategoryRepository = subCategoryRepository;
        this.userService = userService;
        this.itemService = itemService;
        this.categoryService = categoryService;
    }


    public SubCategoryEntity findById(Integer id) {
        return subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategoryEntity", "id", id));
    }
    @Transactional
    public SubCategoryEntity createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryService.findById(categoryDto.getGroupId());

        return new SubCategoryEntity(categoryDto.getName(), categoryEntity);
    }

    public void deleteCategory(Integer categoryId) {
        SubCategoryEntity subCategoryEntity = this.findById(categoryId);
        for (ItemEntity itemEntity : subCategoryEntity.getItemEntityList()) {
            deletedImagesPathService.saveDeletedImagesPathFromImageList(itemEntity.getImageEntityList());
            for (ImageEntity img : itemEntity.getImageEntityList()) {
                imageRepository.delete(img);
            }
            itemRepository.delete(itemEntity);
        }
        subCategoryRepository.delete(subCategoryEntity);

    }

    public ModelAndView getCategoriesWithTheirGroups(ModelAndView view) {
        view.addObject("electronicsGroup", categoryService.findByName("Electronics"));
        view.addObject("realEstateGroup", categoryService.findByName("Real Estate"));
        view.addObject("vehiclesGroup", categoryService.findByName("Vehicle"));
        view.addObject("servicesGroup", categoryService.findByName("Services"));
        view.addObject("jobsGroup", categoryService.findByName("Jobs"));
        view.addObject("fashionGroup", categoryService.findByName("Fashion"));
        view.addObject("toolsAndMaterialsGroup", categoryService.findByName("Tools and Materials"));
        view.addObject("householdGroup", categoryService.findByName("Household"));
        view.addObject("forKidsGroup", categoryService.findByName("For Kids"));
        view.addObject("cultureAndHobbyGroup", categoryService.findByName("Culture and Hobby"));
        view.addObject("appliancesGroup", categoryService.findByName("Appliances"));
        view.addObject("everythinkElseGroup", categoryService.findByName("Everythink Else"));
        return view;
    }

    public Long getCountofSubCategories() {

        return subCategoryRepository.count();
    }
    public List<SubCategoryEntity> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

}
