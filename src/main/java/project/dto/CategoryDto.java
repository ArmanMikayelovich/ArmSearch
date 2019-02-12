package project.dto;

import lombok.Data;


/**
 * օգտագործվում է ֆռոնտից նոր կատեգորիա ստանալու համար
 * և վերափոխվում է CategoryEntity օբյեկտի
 */
@Data
public class CategoryDto {

    private String id;
    private String name;
    private String group;
}

