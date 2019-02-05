/**
 * This class is for images
 * of each announcement
 */

package project.model;

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
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_path", nullable = false, unique = true) //TODO jshtel unique petq te che, xosqi ete jnjenq heto et nuyn tex@ noric
    private String filePath;

    @ManyToOne
    private ProductEntity productEntity;

    public ImageEntity() {  }

    public ImageEntity(String filePath, ProductEntity productEntity) {
        this.filePath = filePath;
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
