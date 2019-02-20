package project.service;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.model.DeletedImagesPath;

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
            List<DeletedImagesPath> dIPs = deletedImagesPathService.findAllDeletedImagesPaths();

            for (DeletedImagesPath dPs : dIPs) {

                File file = new File(dPs.getFilePath());
                file.delete();
            }


            deletedImagesPathService.truncate();
        }
    }
}