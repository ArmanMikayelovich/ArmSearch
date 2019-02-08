package project.dto;

import project.model.ImageEntity;

import java.util.List;

public class ItemDto {
    private int id;

    private String title;
    private String category;
    private String description;
    private List<ImageEntity> imageList;
    //TODO Ani AYSTEX PETQ E LINI NAYEV ITEM-IN KPAC USER@


    public ItemDto(int id, String category, String title, String description, List<ImageEntity> images) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
//        for (ImageEntity images : imageList) {
//        }
//Nkarneri IDner@ hamnknum en anvan het? //TODO,  AYO, HAM@!!

    }

    public int getId() {
        return id;
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

}
