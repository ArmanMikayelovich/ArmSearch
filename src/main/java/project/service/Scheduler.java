/**
 * this scheduler is for deleting useless images
 * from filesystem every 24 hours
 */

package project.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.model.DeletedImagesPathEntity;

import java.io.File;
import java.util.List;

@Data
@Component
public class Scheduler {

    private final DeletedImagesPathService deletedImagesPathService;

    @Autowired
    public Scheduler(DeletedImagesPathService deletedImagesPathService) {
        this.deletedImagesPathService = deletedImagesPathService;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void startScheduler() {
        if(!(deletedImagesPathService.findAllDeletedImagesPaths().isEmpty())) {
            List<DeletedImagesPathEntity> dIPs = deletedImagesPathService.findAllDeletedImagesPaths();

            for (DeletedImagesPathEntity dPs : dIPs) {

                File file = new File(dPs.getFilePath());
                file.delete();
            }


            deletedImagesPathService.truncate();
        }
    }
}