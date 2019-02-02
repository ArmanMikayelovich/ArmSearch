package project.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "categoryGroup")
@Data
public class CategoryGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

}
