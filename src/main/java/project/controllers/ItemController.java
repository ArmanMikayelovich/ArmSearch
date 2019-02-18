package project.controllers;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

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


    /**
     * this method is only for testing
     * @return
     */
    @GetMapping("/addItem")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        modelAndView.addObject("categories", categoryRepository.findAll());
        modelAndView.addObject("groups", itemService.findAll());

        return modelAndView;
    }


    // CreatgetOriginalFilenaee a new Product

    @PostMapping(value = "/items/add", consumes = "multipart/form-data")
    public void createItem(ItemDto itemDto, MultipartFile[] filesToUpload, HttpServletResponse response) {
               Item item =  itemService.addItem(itemDto,filesToUpload);
        try {
             response.sendRedirect(item.getId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO REDIRECT TO CREATED ITEM'S PAGE, OK???

    }


    // Get a Single Product
    @GetMapping("/items/{id}")
    public ModelAndView getItemById(@PathVariable(value = "id") Long itemId) {
        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item", itemService.findById(itemId));
        modelAndView.addObject("dir", System.getProperty("user.dir"));
        return modelAndView;
    }//TODO ANI ItemDto - um avelacnel price... sarqel AnnouncementView.html shablon@


    // Update a Product
    @PostMapping("/items/update")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                              @Valid  ItemDto itemDetails) {

       return itemService.changeItem(itemId, itemDetails);
    }

    // Delete a Product
    @DeleteMapping("/items/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }



    // Delete an Image
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
