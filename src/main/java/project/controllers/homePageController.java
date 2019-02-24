package project.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.Item;
import project.model.User;
import project.service.CategoryGroupService;
import project.service.CategoryService;
import project.service.ItemService;
import project.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
@RequestMapping
public class homePageController {
    private final CategoryService categoryService;
    private final CategoryGroupService categoryGroupService;
    private final ItemService itemService;

    private final UserService userService;

    public homePageController(CategoryService categoryService, CategoryGroupService categoryGroupService,
                              ItemService itemService, UserService userService) {
        this.categoryService = categoryService;
        this.categoryGroupService = categoryGroupService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home", "/items", "/categories"})
    public ModelAndView getHomePage(@RequestParam(required = false) Integer catId,
                                    @RequestParam(required = false) String n,
                                    @PageableDefault(sort = {"updatedAt"}, direction = Sort.Direction.DESC, size = 9) Pageable page) {
        ModelAndView view = new ModelAndView("Home");
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

        if(n==null && catId==null) {
            view.addObject("randomItemList", itemService.getRandomItems());
        }else if (catId != null) {
            Page<Item> items = itemService.findAllByTitleOrDescription(n, page);
            int totalPages = items.getTotalPages();
            if(totalPages>1){
                List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
                view.addObject("pageNumbers", pageNumbers);
                view.addObject("page", "categories");
                view.addObject("catId", catId);
            }
            view.addObject("randomItemList", itemService.findAllByCategory(catId, page));
        }else if(n!=null){
            Page<Item> items = itemService.findAllByTitleOrDescription(n, page);
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
            User auth = userService.getAuthenticatedUser();
            view.addObject("user", auth);

        } catch (Exception e) {
            User adminpage = userService.getUserById(1);
            view.addObject("user", adminpage);

        }
        view.addObject("user", userService.getAuthenticatedUser());
        return view;
    }
/*
    @GetMapping("/items")
    public ModelAndView items(@RequestParam String n , @PageableDefault(sort = {"updatedAt"}, direction = Sort.Direction.DESC, size = 12) Pageable page){
        ModelAndView view = new ModelAndView("items");
        Page<Item> items = itemService.findAllByTitleOrDescription(n, page);

        int totalPages = items.getTotalPages();
        if(totalPages>1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            view.addObject("pageNumbers", pageNumbers);
            view.addObject("n", n);
        }
        *//*Random view*//*
        view.addObject("randomItems", itemService.getRandomItems());
        *//**//*
        view.addObject("items", items);
        return view;
    }*/

}
