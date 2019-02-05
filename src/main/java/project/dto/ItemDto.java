package project.dto;

import project.model.ImageEntity;
import project.model.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ItemDto {
    private int id;

    private String title;
    private String category;
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
