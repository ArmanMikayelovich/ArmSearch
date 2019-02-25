package project.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private  CategoryService categoryService;
    private final UserRepository userRepository;
//    private final UserService userService;
    private final ImageService imageService;
    private final UserService userService;
    public ItemService(ItemRepository itemRepository,
                       UserRepository userRepository, ImageService imageService, UserService userService) {
        this.itemRepository = itemRepository;

        this.userRepository = userRepository;
//        this.userService = userService;
        this.imageService = imageService;
        this.userService = userService;
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


        Item updatedItem = itemRepository.save(item);
        return updatedItem;
    }

    @Transactional
    public void deleteItem(Long id) throws Exception {


        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        //Check, is this logined user created or is admin
        if (userService.getAuthenticatedUser() == item.getUser()
                || userService.getAuthenticatedUser().getRoleName().equals("ADMIN")) {

            imageService.deleteAllImages(item);
            itemRepository.delete(item);
        }

    }
    public void deleteItem(Item item) {
        imageService.deleteAllImages(item);
        itemRepository.delete(item);
        itemRepository.flush();

    }





    public List<Item> getRandomItems(){
        return itemRepository.getRandomItems();
    }


    public Long getCountOfItems() {
        return itemRepository.count();
    }



    public Page<Item> findAllByTitleOrDescription(String titleOrDescription, Pageable pageable){

        Page<Item> allByTitleOrDescription = itemRepository.findAllByTitleOrDescription(titleOrDescription, pageable);
        /* List<Item> items = StreamSupport.stream(allByTitleOrDescription.spliterator(), false)
                .distinct()
                .collect(Collectors.toList());*/
        return  allByTitleOrDescription;
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
