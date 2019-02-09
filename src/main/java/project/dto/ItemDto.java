package project.dto;

import project.model.Image;
import project.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDto {

    private int id;
    private String title;
    private String category;
    private String description;
    private Integer price;
    private List<Image> imageList;
    private ArrayList<String> imagepath;
    public ItemDto(String email, String name, String title, String description, Item productEntity) {
        this.title = this.title;
        this.category = category;
        this.description = this.description;
        this.price = price;
        for (Image images : imageList) {
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