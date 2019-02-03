/**
 * This class is for images
 * of each announcement
 */

package project.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String filePath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private ProductEntity productEntity;

    public ImageEntity(String filePath, ProductEntity productEntity) {
        this.filePath = filePath;
        this.productEntity = productEntity;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", productEntity=" + productEntity +
                '}';
    }
}
