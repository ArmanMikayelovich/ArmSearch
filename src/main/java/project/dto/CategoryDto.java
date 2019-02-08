package project.dto;

import lombok.*;


/**
 * օգտագործվում է ֆռոնտից նոր կատեգորիա ստանալու համար
 * և վերափոխվում է CategoryEntity օբյեկտի
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private String group;

    public CategoryDto(String name, String group) {
        this.name = name;
        this.group = group;
    }
}
