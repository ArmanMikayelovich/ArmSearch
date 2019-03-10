package project.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.ItemEntity;
import project.model.UserEntity;
import project.service.CategoryService;
import project.service.SubCategoryService;
import project.service.ItemService;
import project.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
@RequestMapping
public class HomePageController {
    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    private final UserService userService;

    public HomePageController(SubCategoryService subCategoryService, CategoryService categoryService,
                              ItemService itemService, UserService userService) {
        this.subCategoryService = subCategoryService;
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home", "/items", "/categories"})
    public ModelAndView getHomePage(@RequestParam(required = false) Integer catId,
                                    @RequestParam(required = false) String n,
                                    @PageableDefault(sort = {"updatedAt"}, direction = Sort.Direction.DESC, size = 9) Pageable page) {
        ModelAndView view = new ModelAndView("Home");
        view = subCategoryService.getCategoriesWithTheirGroups(view);
         view= getAllHomePageFunctions(view, catId,n, page);
         return view;
    }

    public ModelAndView getAllHomePageFunctions(ModelAndView view,Integer catId,String n,Pageable page) {
        view = subCategoryService.getCategoriesWithTheirGroups(view);

        if(n==null && catId==null) {
            view.addObject("randomItemList", itemService.getRandomItems());

        }else if (catId != null) {
            Page<ItemEntity> items = itemService.findAllByTitleOrDescription(n, page);
            int totalPages = items.getTotalPages();
            if(totalPages>1){
                List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
                view.addObject("pageNumbers", pageNumbers);
                view.addObject("page", "categories");
                view.addObject("catId", catId);
            }
            view.addObject("randomItemList", itemService.findAllBySubCategory(catId, page));
        }else if(n!=null){
            Page<ItemEntity> items = itemService.findAllByTitleOrDescription(n, page);
            int totalPages = items.getTotalPages();
            if(totalPages>1){
                List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
                view.addObject("pageNumbers", pageNumbers);
                view.addObject("page", "items");
                view.addObject("n", n);
            }

            view.addObject("randomItemList", items);
        }

        try{

            UserEntity auth = userService.getAuthenticatedUser();
            view.addObject("user", auth);

        } catch (Exception e) {
            UserEntity userEntity = new UserEntity(); userEntity.setId(1);
            view.addObject("userEntity", userEntity);

        }
        try {
            view.addObject("user", userService.getAuthenticatedUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


}
