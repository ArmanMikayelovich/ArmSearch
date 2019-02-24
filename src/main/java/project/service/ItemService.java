package project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.dto.ItemDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.Image;
import project.model.Item;
import project.model.User;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.io.IOException;
import java.util.List;
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
//    private final UserService userService;
    private final ImageService imageService;

    public ItemService(ItemRepository itemRepository, CategoryService categoryService,
                       UserRepository userRepository, ImageService imageService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
//        this.userService = userService;
        this.imageService = imageService;
    }
    public Item findById(Long id) {
       return itemRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
    }
    public List<Item> findAll() {
       return itemRepository.findAll();
    }

    @Transactional
    public Item addItem(ItemDto itemDto, MultipartFile[] images) {
        System.out.println(itemDto.toString());
        Item item = new Item();

        //SET fields
        item.setTitle(itemDto.getTitle());
        item.setDescription(itemDto.getDescription());
        item.setPrice(Double.valueOf( itemDto.getPrice() ) );//TODO ANI MUST BE ACCEPT ONLY NUMBERS

        Category category = categoryService.findById(itemDto.getCategoryId());
        item.setCategory(category);

//        User user = userService.getAuthenticatedUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
        User user = userRepository.findByEmail(springUser.getUsername());
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
        return item;

    }
    @Transactional
    public Item changeItem(Long itemId,ItemDto itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setCategory(categoryService.findById(itemDetails.getCategoryId()));

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

    public List<Item> getRamdomItems(int countOfItems) {
        //TODO ARO SHTAAAAAAAAAAAP!!!!!!!!!!!!!
//        return null;

        List<Item> list = itemRepository.findAll();
        return list;

    }

    public Page<Item> findAllByTitleOrDescription(String titleOrDescription, Pageable pageable){

        Page<Item> allByTitleOrDescription = itemRepository.findAllByTitleOrDescription(titleOrDescription, pageable);
        /* List<Item> items = StreamSupport.stream(allByTitleOrDescription.spliterator(), false)
                .distinct()
                .collect(Collectors.toList());*/
        return  allByTitleOrDescription;
    }

    public List<Item> getRandomItems(){
        return itemRepository.getRandomItems();
    }

    public void save(Item item){
        itemRepository.save(item);
    }

    /*RAFAEL*/
    public Page<Item> findAllByCategory(Integer catId, Pageable pageable){
        Category category = categoryService.findById(catId);
        return itemRepository.findAllByCategory(category, pageable);
    }
}
