package project.dto;

import lombok.Data;

import lombok.NoArgsConstructor;
import project.model.ImageEntity;
import project.model.ItemEntity;

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

    private List<ImageEntity> imageEntityList; // TODO Arman jshtel esi petqa te che?
    private List<String> imagepath;

    //TODO Ani u Arman sranic nerqev bacatrel anhrajeshtutyun@

    public ItemDto(String email, String name, String title, String description, ItemEntity itemEntity) {
        this.title = this.title;
        this.category = category;
        this.description = this.description;
        this.price = price;
        for (ImageEntity images : imageEntityList) {
            imagepath.add(images.getFilePath());
        }
    }

    public ItemDto(String title, Integer categoryId, String description, Double price, List<ImageEntity> imageEntityList) {
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.imageEntityList = imageEntityList;
    }

    public ItemDto(ItemEntity itemEntity) {
        this.id = (Integer.valueOf(itemEntity.getId().toString()));
        this.title = itemEntity.getTitle();
        this.category = itemEntity.getSubCategoryEntity().getName();
        this.categoryId = itemEntity.getSubCategoryEntity().getId();
        this.description = itemEntity.getDescription();
        this.price = itemEntity.getPrice();
        for (ImageEntity images : itemEntity.getImageEntityList()) {
            imagepath.add(images.getFilePath());
        }
    }


}



