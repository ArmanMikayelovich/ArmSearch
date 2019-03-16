package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.dto.ItemDto;
import project.model.ItemEntity;
import project.model.UserEntity;
import project.repository.SubCategoryRepository;
import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.ImageService;
import project.service.ItemService;
import project.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;

@RestController
@RequestMapping
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    private final ItemService itemService;
    @Autowired
    ImageService imageService;
    @Autowired
    private UserService userService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }



    @GetMapping("/addItem")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        modelAndView.addObject("categories", subCategoryRepository.findAll());
        modelAndView.addObject("groups", itemService.findAll());

        return modelAndView;
    }



    @PostMapping(value = "/addItem", consumes = "multipart/form-data")
    public void createItem(ItemDto itemDto, MultipartFile[] filesToUpload, HttpServletResponse response) {
               ItemEntity itemEntity =  itemService.addItem(itemDto,filesToUpload);
        try {

            String url = "/items/" + itemEntity.getId().toString();
             response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    // Get a Single Product
    @GetMapping("/items/{id}")
    public ModelAndView getItemById(@PathVariable(value = "id") Long itemId, Principal principal) {

        ItemEntity itemEntity =itemService.findById(itemId);


        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("itemEntity",itemEntity);
        return modelAndView;
    }

    // Update a Product
    @PostMapping("/updateItem")
    public ItemEntity updateItem(@PathVariable(value = "id") Long itemId,
                                 @Valid  ItemDto itemDetails) {

       return itemService.changeItem(itemId, itemDetails);
    }

    // Delete a ItemEntity
    @PostMapping("/deleteItem/{id}")
    public void deleteItem(@PathVariable(value = "id") Long itemId, HttpServletResponse response) throws Exception {
        UserEntity u = itemRepository.findById(itemId).get().getUserEntity();
        if (userService.getAuthenticatedUser() == itemRepository.findById(itemId).get().getUserEntity() ||
                userService.getAuthenticatedUser().getRoleName().equals("ADMIN")) {

            itemService.deleteItem(itemId);
        }
        if (userService.getAuthenticatedUser().getRoleName().equals("ADMIN")) {
            response.sendRedirect("/admin/items/");
        }
       else response.sendRedirect("/users/" + u.getId());
        }



    // Delete an ImageEntity
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
