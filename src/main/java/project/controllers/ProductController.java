package project.controllers;

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
import project.model.ProductEntity;
import project.repository.ProductRepository;

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

    // Get All Products
    @GetMapping("/products")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("addItem");
        List items = productRepository.findAll().stream()
                .map(p -> new ItemDto(
                        p.getUserEntity().getEmail(),
                        p.getCategoryEntity().getName(),
                        p.getTitle(),
                        p.getDescription(),
                        null)).
                        collect(Collectors.toList());

        modelAndView.addObject("items", items);

        return modelAndView;
    }
    //TODO testavorumic heto jnjel

    // CreatgetOriginalFilenamee a new Product
    @PostMapping(value = "/products", consumes = "multipart/form-data")
    public ModelAndView createProduct(ItemDto itemDto,MultipartFile[] filesToUpload) {
        System.out.println(itemDto.toString());
        for (MultipartFile file: filesToUpload ) {
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
