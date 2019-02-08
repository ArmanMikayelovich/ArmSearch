/**
 * This class describes in which
 * groups are divided categories
 */
package project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class CategoryGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50,name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryGroupEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = false)
    private List<CategoryEntity> categories;

    public CategoryGroupEntity(String name, List<CategoryEntity> categories) {
        this.name = name;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoryGroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                '}';
    }
}
