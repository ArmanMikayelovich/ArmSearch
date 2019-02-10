package project.dto;

import project.model.Image;
import project.model.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class    ItemDto {

    private int id;
    private String userEmail;
    private String title;
    private String category;
    private Integer categoryId;
    private String description;
    private Double price;
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

    public ItemDto(String title, Integer categoryId, String description, Double price, List<Image> imageList) {
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.imageList = imageList;
    }

    public ItemDto(Item item) {
        this.id = (Integer.valueOf(item.getId().toString()));
        this.title = item.getTitle();
        this.category = item.getCategory().getName();
        this.categoryId = item.getCategory().getId();
        this.description = item.getDescription();
        this.price = item.getPrice();
        for (Image images : item.getImageList()) {
            imagepath.add(images.getFilePath());
        }
    }


}



