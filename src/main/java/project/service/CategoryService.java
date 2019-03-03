package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import project.dto.CategoryDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.CategoryGroup;
import project.repository.CategoryRepository;
import project.repository.ImageRepository;
import project.repository.ItemRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    @Autowired
    private  ItemService itemService;
    @Autowired
    DeletedImagesPathService deletedImagesPathService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ItemRepository itemRepository;

    private final CategoryGroupService categoryGroupService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService, ItemService itemService, CategoryGroupService categoryGroupService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.itemService = itemService;
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

        return new Category(categoryDto.getName(), categoryGroup);
    }
    @Transactional()
    public void deleteCategory(Integer categoryId) {
        Category category = this.findById(categoryId);
        category.getItemList()
                .forEach(item -> {
                    deletedImagesPathService.saveDeletedImagesPathFromImageList(item.getImageList());
                    item.getImageList().forEach(imageRepository::delete);
                    });

        categoryRepository.delete(category);

    }

    public ModelAndView getCategoriesWithTheirGroups(ModelAndView view) {
        view.addObject("electronicsGroup", categoryGroupService.findByName("Electronics"));
        view.addObject("realEstateGroup", categoryGroupService.findByName("Real Estate"));
        view.addObject("vehiclesGroup", categoryGroupService.findByName("Vehicle"));
        view.addObject("servicesGroup", categoryGroupService.findByName("Services"));
        view.addObject("jobsGroup", categoryGroupService.findByName("Jobs"));
        view.addObject("fashionGroup", categoryGroupService.findByName("Fashion"));
        view.addObject("toolsAndMaterialsGroup", categoryGroupService.findByName("Tools and Materials"));
        view.addObject("householdGroup", categoryGroupService.findByName("Household"));
        view.addObject("forKidsGroup", categoryGroupService.findByName("For Kids"));
        view.addObject("cultureAndHobbyGroup", categoryGroupService.findByName("Culture and Hobby"));
        view.addObject("appliancesGroup", categoryGroupService.findByName("Appliances"));
        view.addObject("everythinkElseGroup", categoryGroupService.findByName("Everythink Else"));
        return view;
    }

    public Long getCountofCategories() {

        return categoryRepository.count();
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


}
