/**
 * This class describes which
 * categories can be the announcements
 */
package project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //TODO Check with Vahe's help ManyToMany
    private List<ProductEntity> productList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryGroup_id")
    private CategoryGroupEntity categoryGroupEntity;

    public CategoryEntity(String name, List<ProductEntity> productList, CategoryGroupEntity categoryGroupEntity) {
        this.name = name;
        this.productList = productList;
        this.categoryGroupEntity = categoryGroupEntity;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productList=" + productList +
                ", categoryGroupEntity=" + categoryGroupEntity +
                '}';
    }
}
