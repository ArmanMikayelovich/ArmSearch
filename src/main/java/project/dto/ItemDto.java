package project.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.model.ImageEntity;
import project.model.ProductEntity;


import project.model.ImageEntity;
import project.model.ProductEntity;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemDto {
    private int id;
    private String userEmail;
    private String title;
    private String category;
    private Integer categoryId;
    private String description;
    private Integer price;
    private List<ImageEntity> imageList;
    private ArrayList<String> imagepath;
    public ItemDto(ProductEntity productEntity) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        for (ImageEntity images : imageList) {
            imagepath.add(images.getFilePath());
        }


    }

    public ItemDto(String title, Integer categoryId, String description, Integer price, List<ImageEntity> imageList) {
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.imageList = imageList;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }


}
