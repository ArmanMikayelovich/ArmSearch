/**
 * This class is for images
 * of each announcement
 */

package project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_path", nullable = false, unique = true) //TODO jshtel unique petq te che, xosqi ete jnjenq heto et nuyn tex@ noric
    private String filePath;

    @ManyToOne
    private Item item;
}
