package project.service;

import lombok.Data;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.DeletedImagesPath;
import project.model.Image;
import project.repository.DeletedImagesPathRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Data
public class DeletedImagesPathService {

    private  Session session;


    public void deletedImagesPathsaver(List<Image> images){

        for (Image img: images) {

            DeletedImagesPath DIP = new DeletedImagesPath();
            DIP.setFilePath(img.getFilePath());
        }
    }


    public List<DeletedImagesPath> findAllDeletedImagesPathsWithCriteriaQuery() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<DeletedImagesPath> cq = cb.createQuery(DeletedImagesPath.class);
        Root<DeletedImagesPath> rootEntry = cq.from(DeletedImagesPath.class);
        CriteriaQuery<DeletedImagesPath> all = cq.select(rootEntry);

        TypedQuery<DeletedImagesPath> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    public int hqlTruncate(String table){
        String hql = String.format("delete from %s",table);
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

}
