/**
 * This class describes which
 * categories can be the announcements
 */
package project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import java.util.List;

@Data
@Entity
public class Category {
    public Category(String name, CategoryGroup categoryGroup) {
        this.name = name;
        this.categoryGroup = categoryGroup;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //TODO Check with Vahe's help ManyToMany
    private List<Item> itemList;

    @ManyToOne
    @JoinColumn(name = "categoryGroup_id")
    private CategoryGroup categoryGroup;
}
