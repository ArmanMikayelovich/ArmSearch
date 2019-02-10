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
import project.model.CategoryEntity;
import project.model.ImageEntity;
import project.model.ProductEntity;
import project.model.UserEntity;
import project.repository.CategoryRepository;
import project.repository.ImageRepository;
import project.repository.ProductRepository;
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
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;


    // Get All Products
    @GetMapping("/products")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        List items = productRepository.findAll().stream()
                .map(p -> new ItemDto(
                        p.getUserEntity().getEmail(),
                        p.getCategoryEntity().getId(),
                        p.getTitle(),
                        p.getDescription(),
                        null)).
                        collect(Collectors.toList());

        modelAndView.addObject("items", items);
        modelAndView.addObject("groups", categoryRepository.findAll());

        return modelAndView;
    }
    //TODO testavorumic heto jnjel

    // CreatgetOriginalFilenamee a new Product
    @PostMapping(value = "/products", consumes = "multipart/form-data")
    public ModelAndView createProduct(ItemDto itemDto,List<MultipartFile> filesToUpload) {
        System.out.println(itemDto.toString());
        ProductEntity productEntity = new ProductEntity();
        //SET fields
        productEntity.setTitle(itemDto.getTitle());
        productEntity.setDescription(itemDto.getDescription());
        productEntity.setPrice(Double.valueOf( itemDto.getPrice() ) );//TODO ANI MUST BE ACCEPT ONLY NUMBERS
        CategoryEntity categoryEntity = categoryRepository.findById(itemDto.getCategory()).get();
        productEntity.setCategoryEntity(categoryEntity);
        //todo set category ok
        UserEntity userEntity = userRepository.findByEmail(itemDto.getUserEmail()).get(0);
        productEntity.setUserEntity(userEntity);
        //todo set user ok
        int count = 0;
        for (MultipartFile file: filesToUpload ) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setProductEntity(productEntity);

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            productRepository.save(productEntity);
            String fileName = "img/" + productEntity.getId() + "_" + count + "." + fileExtension;

            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    System.out.println(System.getProperties());
                    File img = new File(fileName);
                    img.createNewFile();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(img));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageEntity.setFilePath(fileName);
                imageRepository.save(imageEntity);
                productEntity.getImageList().add(imageEntity);
            } else {
            }
        }
        productRepository.save(productEntity);

        ModelAndView modelAndView = new ModelAndView("addItem");

        return modelAndView;
    }


    // Get a Single Product
    @GetMapping("/products/{id}")
    public ProductEntity getProductById(@PathVariable(value = "id") Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }//TODO ANI ItemDto - um avelacnel price... sarqel AnnouncementView.html shablon@


    // Update a Product
    @PutMapping("/products/{id}")
    public ProductEntity updateProduct(@PathVariable(value = "id") Long productId,
                                 @Valid @RequestBody ProductEntity productDetails) {

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        product.setTitle(productDetails.getTitle());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategoryEntity(productDetails.getCategoryEntity());
        product.setUserEntity(productDetails.getUserEntity());
        product.setImageList(productDetails.getImageList());

        ProductEntity updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    // Delete a Product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productRepository.delete(product);

        return ResponseEntity.ok().build();
    }
}
