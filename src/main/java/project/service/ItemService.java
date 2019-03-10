/**
 * this class calls the methods of ItemRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.dto.ItemDto;
import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.model.ItemEntity;
import project.model.SubCategoryEntity;
import project.model.UserEntity;
import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.io.IOException;
import java.util.List;
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    @Autowired
    private SubCategoryService subCategoryService;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserService userService;
    @Autowired
    DeletedImagesPathService deletedImagesPathService;
    @Autowired
    private ImageRepository imageRepository;


    public ItemService(ItemRepository itemRepository,
                       UserRepository userRepository, ImageService imageService, UserService userService) {
        this.itemRepository = itemRepository;

        this.userRepository = userRepository;
        this.imageService = imageService;
        this.userService = userService;
    }
    public ItemEntity findById(Long id) {

       ItemEntity itemEntity = itemRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("ItemEntity", "id", id));

       Long countOfViews = itemEntity.getCountOfViews();
       countOfViews++;
        itemEntity.setCountOfViews(countOfViews);
        this.save(itemEntity);

        return itemEntity;
    }

    public boolean isCreator(ItemEntity itemEntity) {
        return (this.getAuthenticatedUser() == itemEntity.getUserEntity());
    }
    public List<ItemEntity> findAll() {
       return itemRepository.findAll();
    }

    @Transactional
    public ItemEntity addItem(ItemDto itemDto, MultipartFile[] images) {
        System.out.println(itemDto.toString());
        ItemEntity itemEntity = new ItemEntity();

        itemEntity.setTitle(itemDto.getTitle());
        itemEntity.setDescription(itemDto.getDescription());
        itemEntity.setPrice(Double.valueOf( itemDto.getPrice() ) );

        SubCategoryEntity subCategoryEntity = subCategoryService.findById(itemDto.getCategoryId());
        itemEntity.setSubCategoryEntity(subCategoryEntity);

        try {
            UserEntity userEntity = userService.getAuthenticatedUser();

//
        itemEntity.setUserEntity(userEntity);
        userRepository.save(userEntity);
        itemRepository.save(itemEntity);
        int images_count = 0;

            for (MultipartFile file: images ) {
                try {
                   ImageEntity imageEntity =  imageService.addImage(itemEntity,file,images_count++);
                    itemEntity.getImageEntityList().add(imageEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        itemRepository.save(itemEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemEntity;

    }
    @Transactional
    public ItemEntity changeItem(Long itemId, ItemDto itemDetails) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("ItemEntity", "id", itemId));

        itemEntity.setTitle(itemDetails.getTitle());
        itemEntity.setDescription(itemDetails.getDescription());
        itemEntity.setPrice(itemDetails.getPrice());
        itemEntity.setSubCategoryEntity(subCategoryService.findById(itemDetails.getCategoryId()));


        ItemEntity updatedItemEntity = itemRepository.save(itemEntity);
        return updatedItemEntity;
    }

    @Transactional
    public void deleteItem(Long id)  {
        UserEntity userEntity = this.getAuthenticatedUser();

        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemEntity", "id", id));

        if (userEntity == itemEntity.getUserEntity()
                || userEntity.getRoleName().equals("ADMIN")) {

            deletedImagesPathService.saveDeletedImagesPathFromImageList(itemEntity.getImageEntityList());
           itemEntity.getImageEntityList().forEach(img -> {
                            imageRepository.delete(img);
                                 });
           itemRepository.delete(itemEntity);

        }

    }
    @Deprecated
    public void deleteItem(ItemEntity itemEntity) {
        imageService.deleteAllImages(itemEntity);
        itemRepository.delete(itemEntity);

    }

    public List<ItemEntity> getRandomItems(){

        return itemRepository.getRandomItems();
    }


    public Long getCountOfItems() {

        return itemRepository.count();
    }



    public Page<ItemEntity> findAllByTitleOrDescription(String titleOrDescription, Pageable pageable){

        Page<ItemEntity> allByTitleOrDescription = itemRepository.findAllByTitleOrDescription(
                titleOrDescription, pageable);

        return  allByTitleOrDescription;
    }



    public void save(ItemEntity itemEntity){

        itemRepository.save(itemEntity);
    }

    public Page<ItemEntity> findAllBySubCategory(Integer catId, Pageable pageable){
        SubCategoryEntity subCategoryEntity = subCategoryService.findById(catId);
        return itemRepository.findAllBySubCategoryEntity(subCategoryEntity, pageable);
    }

    private UserEntity getAuthenticatedUser() {
        try {
            return userService.getAuthenticatedUser();
        } catch (Exception e) {
            throw new IllegalStateException("No logged in userEntity");
        }
    }

    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
