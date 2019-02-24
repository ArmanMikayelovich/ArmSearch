package project.dto;

import lombok.Data;

import lombok.NoArgsConstructor;
import project.model.Image;
import project.model.Item;

import java.util.List;

@Data
@NoArgsConstructor
public class    ItemDto {

    private int id;
    private String title;
    private String category;
    private Integer categoryId;
    private String description;
    private Double price;

    private List<Image> imageList; // TODO Arman jshtel esi petqa te che?
    private List<String> imagepath;

    //TODO Ani u Arman sranic nerqev bacatrel anhrajeshtutyun@

    public ItemDto(String email, String name, String title, String description, Item item) {
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



