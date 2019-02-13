package project.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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
import project.service.ItemService;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    List<Item> items = ItemRepository.findBySearchTag("esiminch"); //TODO ay es Listn el petq e veradarcvi
    // zapros anoxin: web, isk esiminch@ pti zaprosov ga hasni back

    /**
     * this method is only for testing
     * @return
     */
//    @GetMapping("/items")
//    public ModelAndView getAllProducts() {
//        ModelAndView modelAndView = new ModelAndView("addItem");
//        List items = itemService.findAll().stream()
//                .map(p -> new ItemDto(p)).collect(Collectors.toList());
//
//        modelAndView.addObject("items", items);
//        modelAndView.addObject("groups", itemService.findAll());
//
//        return modelAndView;
//    }
    //TODO testavorumic heto jnjel

    // CreatgetOriginalFilenaee a new Product
    @PostMapping(value = "/items/add", consumes = "multipart/form-data")
    public ModelAndView createItem(ItemDto itemDto, MultipartFile[] filesToUpload) {
     itemService.addItem(itemDto,filesToUpload);
        ModelAndView modelAndView = new ModelAndView("addItem");

        return modelAndView;
    }


    // Get a Single Product
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable(value = "id") Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemId));
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
}
