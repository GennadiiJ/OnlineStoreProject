package ua.dao;

import org.springframework.stereotype.Repository;
import ua.domain.Category;
import ua.domain.Picture;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void delete(long id) {
        Category c = entityManager.getReference(Category.class, id);
        entityManager.remove(c);
    }

    @Override
    public Category findOne(long id) {
        return entityManager.getReference(Category.class, id);
    }

    @Override
    public List<Category> list() {
        Query query = entityManager.createQuery("SELECT g FROM Category g", Category.class);
        return (List<Category>) query.getResultList();
    }

    @Override
    public byte[] getPictureBody(long id) {
        try {
            Query query = entityManager.createQuery("SELECT c FROM Picture c WHERE c.id = :id", Picture.class);
            query.setParameter("id", id);
            Picture picture = (Picture) query.getSingleResult();
            return picture.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
