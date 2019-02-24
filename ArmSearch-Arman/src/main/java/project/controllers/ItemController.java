package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.dto.ItemDto;
import project.model.Item;
import project.model.User;
import project.repository.CategoryRepository;
import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.ImageService;
import project.service.ItemService;
import project.service.UserService;
import project.service.CategoryGroupService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

@RestController
@RequestMapping
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    private final ItemService itemService;
    @Autowired
    ImageService imageService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }



    @GetMapping("/addItem")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        modelAndView.addObject("categories", categoryRepository.findAll());
        modelAndView.addObject("groups", itemService.findAll());

        return modelAndView;
    }



    @PostMapping(value = "/addItem", consumes = "multipart/form-data")
    public void createItem(ItemDto itemDto, MultipartFile[] filesToUpload, HttpServletResponse response) {
               Item item =  itemService.addItem(itemDto,filesToUpload);
        try {
            String url = "/items/" + item.getId().toString();
             response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO REDIRECT TO CREATED ITEM'S PAGE, OK???
        //test

    }


    // Get a Single Product
    @GetMapping("/items/{id}")
    public ModelAndView getItemById(@PathVariable(value = "id") Long itemId) {
        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item", itemService.findById(itemId));
        modelAndView.addObject("dir", System.getProperty("user.dir"));
//        modelAndView.addObject("electronicsGroup", categoryGroupService.findByName("Electronics"));
//        modelAndView.addObject("realEstateGroup", categoryGroupService.findByName("Real Estate"));
//        modelAndView.addObject("vehiclesGroup", categoryGroupService.findByName("Vehicle"));
//        modelAndView.addObject("servicesGroup", categoryGroupService.findByName("Services"));
//        modelAndView.addObject("jobsGroup", categoryGroupService.findByName("Jobs"));
//        modelAndView.addObject("fashionGroup", categoryGroupService.findByName("Fashion"));
//        modelAndView.addObject("toolsAndMaterialsGroup", categoryGroupService.findByName("Tools and Materials"));
//        modelAndView.addObject("householdGroup", categoryGroupService.findByName("Household"));
//        modelAndView.addObject("forKidsGroup", categoryGroupService.findByName("For Kids"));
//        modelAndView.addObject("cultureAndHobbyGroup", categoryGroupService.findByName("Culture and Hobby"));
//        modelAndView.addObject("appliancesGroup", categoryGroupService.findByName("Appliances"));
//        modelAndView.addObject("everythinkElseGroup", categoryGroupService.findByName("Everythink Else"));
//        modelAndView.addObject( "randomItemList", itemService.getRamdomItems(555));
//        try{
//            User auth = userService.getAuthenticatedUser();
//            modelAndView.addObject("user", auth);
//
//        } catch (Exception e) {
//            User adminpage = userService.getUserById(1);
//            modelAndView.addObject("user", adminpage);
//
//        }
        return modelAndView;
    }

    // Update a Product
    @PostMapping("/updateItem")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                              @Valid  ItemDto itemDetails) {

       return itemService.changeItem(itemId, itemDetails);
    }

    // Delete a Product
    @DeleteMapping("/deleteItem{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }



    // Delete an Image
    @DeleteMapping("/static/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
