/**
 * this entity ddescribes the table in DB
 * where are paths of no more used images
 * to be deleted every 24 hours
 */

package project.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "deleted_images_paths")
public class DeletedImagesPathEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_path", nullable = false, unique = true)
    private String filePath;
}