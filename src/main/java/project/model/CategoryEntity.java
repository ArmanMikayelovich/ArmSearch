/**
 * This class describes which
 * categories can be the announcements
 */
package project.model;

import javax.persistence.*;

import java.util.List;

@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //TODO Check with Vahe's help ManyToMany
    private List<ProductEntity> productList;

    @ManyToOne
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
