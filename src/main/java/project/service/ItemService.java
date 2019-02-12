package project.service;

import org.springframework.web.multipart.MultipartFile;
import project.dto.ItemDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.Image;
import project.model.Item;
import project.model.User;
import project.repository.ItemRepository;

import java.io.IOException;
import java.util.List;

public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ImageService imageService;

    public ItemService(ItemRepository itemRepository, CategoryService categoryService,
                                UserService userService, ImageService imageService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.imageService = imageService;
    }

    public List<Item> findAll() {
       return itemRepository.findAll();
    }

    public void addItem(ItemDto itemDto, MultipartFile[] images) {
        System.out.println(itemDto.toString());
        Item item = new Item();

        //SET fields
        item.setTitle(itemDto.getTitle());
        item.setDescription(itemDto.getDescription());
        item.setPrice(Double.valueOf( itemDto.getPrice() ) );//TODO ANI MUST BE ACCEPT ONLY NUMBERS

        Category category = categoryService.findById(itemDto.getCategoryId()).get();
        item.setCategory(category);

        User user = userService.findByEmail(itemDto.getUserEmail());
        item.setUser(user);
        itemRepository.save(item);
        int images_count = 0;
        /**
         * adding images to File System && Database
         */
            for (MultipartFile file: images ) {
                try {
                   Image image =  imageService.addImage(item,file,images_count++);
                    item.getImageList().add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        itemRepository.save(item);

    }

    public Item changeItem(Long itemId,ItemDto itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setCategory(categoryService.findById(itemDetails.getCategoryId()).get());

//        item.setUser(itemDetails.getUser());//TODO ARMAN get user with security....
        //todo item's image delete method
        Item updatedItem = itemRepository.save(item);
        return updatedItem;
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        itemRepository.delete(item);
    }
}
