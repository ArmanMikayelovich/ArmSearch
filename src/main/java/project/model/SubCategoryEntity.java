/**
 * This class describes which
 * subcategories can be the announcements
 */
package project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sub_categories")
public class SubCategoryEntity {
    public SubCategoryEntity(String name, CategoryEntity categoryEntity) {
        this.name = name;
        this.categoryEntity = categoryEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "subCategoryEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ItemEntity> itemEntityList;

    @ManyToOne
    private CategoryEntity categoryEntity;
}


