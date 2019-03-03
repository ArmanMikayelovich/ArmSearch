/**
 * This class describes in which
 * groups are divided categories
 */
package project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class CategoryGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

}
