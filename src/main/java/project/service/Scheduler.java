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

@Component
@Data

public class Scheduler {

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void startScheduler() {
       new Thread(new ScheduleThread()).start();
    }
}

@Data

class ScheduleThread implements Runnable {
    private DeletedImagesPathService deletedImagesPathService;

    @Override
    public void run(){

        if(deletedImagesPathService.findAllDeletedImagesPathsWithCriteriaQuery() != null) {
            List<DeletedImagesPath> dIPs = deletedImagesPathService.findAllDeletedImagesPathsWithCriteriaQuery();

            for (DeletedImagesPath dPs : dIPs) {

                File file = new File(dPs.getFilePath());
                file.delete();
            }

            int deleted = deletedImagesPathService.hqlTruncate("deleted_images_pathes");
        }
    }
}