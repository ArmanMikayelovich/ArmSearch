package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.dto.ItemDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.Image;
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
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;

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
    @Autowired
    private UserService userService;

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


    }


    // Get a Single Product
    @GetMapping("/items/{id}")
    public ModelAndView getItemById(@PathVariable(value = "id") Long itemId, Principal principal) {

        Item item=itemService.findById(itemId);

        //TODO move everything from here
        long l;
        try {
             l = item.getCountOfViews();
        } catch (Exception e) {
             l=0L;

        }
      l++;
        item.setCountOfViews(l);
        itemService.save(item);
        //TODO to here to itemService get method

        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item", itemService.findById(itemId));
        modelAndView.addObject("dir", System.getProperty("user.dir"));
        return modelAndView;
    }

    // Update a Product
    @PostMapping("/updateItem")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                              @Valid  ItemDto itemDetails) {

       return itemService.changeItem(itemId, itemDetails);
    }

    // Delete a Product
    @GetMapping("/deleteItem/{id}")
    public void deleteItem(@PathVariable(value = "id") Long itemId, HttpServletResponse response) throws Exception {
        User u = itemRepository.findById(itemId).get().getUser();
        if (userService.getAuthenticatedUser() == itemRepository.findById(itemId).get().getUser() ||
                userService.getAuthenticatedUser().getRoleName().equals("ADMIN")) {

            itemService.deleteItem(itemId);
        }
        response.sendRedirect("/users/" + u.getId());
    }



    // Delete an Image
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
