package project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.model.ImageEntity;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private int id;
    private String userEmail;
    private String title;
    private String category;
    private String description;
    private String price;

    private List<ImageEntity> imageList;
    //TODO Ani AYSTEX PETQ E LINI NAYEV ITEM-IN KPAC USER@


    public ItemDto(String userEmail, String category, String title, String description, List<ImageEntity> images) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
//        for (ImageEntity images : imageList) {
//        }
//Nkarneri IDner@ hamnknum en anvan het? //TODO,  AYO, HAM@!!

    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
