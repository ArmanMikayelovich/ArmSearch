/**
 * This class describes which
 * categories can be the announcements
 */
package project.model;

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
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //TODO Check with Vahe's help ManyToMany
    private List<ProductEntity> productList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_group_id")
    private CategoryGroupEntity categoryGroupEntity;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductEntity> getProductList() {
        return productList;
    }

    public CategoryGroupEntity getCategoryGroupEntity() {
        return categoryGroupEntity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
    }

    public void setCategoryGroupEntity(CategoryGroupEntity categoryGroupEntity) {
        this.categoryGroupEntity = categoryGroupEntity;
    }

    public CategoryEntity() {}

    public CategoryEntity(String name, CategoryGroupEntity categoryGroupEntity) {
        this.name = name;
        this.categoryGroupEntity = categoryGroupEntity;
    }
}
