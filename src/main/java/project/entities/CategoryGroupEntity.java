/**
 * This class describes in which
 * groups are divided categories
 */
package project.entities;

import lombok.Data;

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
import java.util.ArrayList;


@Entity
@Table(name = "categoryGroups")
@Data
public class CategoryGroupEntity {           // TODO jshtel Vaheic grupneri bazhanel@ lav gaxapara te iharke voch

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "categoryGroups", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
