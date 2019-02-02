package Start.entity;

import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {
        //TODO ARO SET NOTNULL

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String description;

    @Column
    private long price;

    @Column
    private Date createDate;

    @Column
    private String title;

    @Column
    private Date updateDate;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "item",fetch = FetchType.EAGER,orphanRemoval = true)
    List<Image> imageList;
    //TODO ARO user ջնջելու հետ նաև ջնջել բոլոր նկարները fileSYstemից
    


}
