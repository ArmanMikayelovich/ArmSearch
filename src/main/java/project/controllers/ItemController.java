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

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;

    /**
     * this method is only for testing
     * @return
     */
    @GetMapping("/items")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        List items = itemRepository.findAll().stream()
                .map(p -> new ItemDto(p)).collect(Collectors.toList());

        modelAndView.addObject("items", items);
        modelAndView.addObject("groups", itemRepository.findAll());

        return modelAndView;
    }
    //TODO testavorumic heto jnjel

    // CreatgetOriginalFilenaee a new Product
    @PostMapping(value = "/items", consumes = "multipart/form-data")
    public ModelAndView createItem(ItemDto itemDto, MultipartFile[] filesToUpload) {
        System.out.println(itemDto.toString());
        Item item = new Item();
        //SET fields
        item.setTitle(itemDto.getTitle());
        item.setDescription(itemDto.getDescription());
        item.setPrice(Double.valueOf( itemDto.getPrice() ) );//TODO ANI MUST BE ACCEPT ONLY NUMBERS
        Category category = categoryRepository.findById(itemDto.getCategoryId()).get();
        item.setCategory(category);
        //todo set category ok
        User user = userRepository.findByEmail(itemDto.getUserEmail()).get(0);
        item.setUser(user);
        //todo set user ok
        int count = 0;
        for (MultipartFile file: filesToUpload ) {
            Image image = new Image();
            image.setItem(item);

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            itemRepository.save(item);
            String fileName = "img/" + item.getId() + "_" + count + "." + fileExtension;

            if (!file.isEmpty()) {
                try {
                    //not finished
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {

                }
            } else {
            }
        }
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
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                              @Valid @RequestBody Item itemDetails) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setCategory(itemDetails.getCategory());
        item.setUser(itemDetails.getUser());
        item.setImageList(itemDetails.getImageList());

        Item updatedItem = itemRepository.save(item);
        return updatedItem;
    }

    // Delete a Product
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        itemRepository.delete(item);

        return ResponseEntity.ok().build();
    }
}
