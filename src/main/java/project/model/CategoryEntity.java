/**
 * This class describes in which
 * categories are divided the subcategories
 */
package project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SubCategoryEntity> subCategoryEntities = new ArrayList<>();

}
