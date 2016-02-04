package ua.dao;

import org.springframework.stereotype.Repository;
import ua.domain.Category;
import ua.domain.Product;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(long id) {
        Query query;
        query = entityManager.createQuery("SELECT c FROM Product c WHERE c.id = :id", Product.class);
        query.setParameter("id", id);
        Product p = (Product) query.getSingleResult();
        entityManager.remove(p);
    }

    @Override
    public Product findOne(long id) {
        Query query;
        query = entityManager.createQuery("SELECT c FROM Product c WHERE c.id = :id", Product.class);
        query.setParameter("id", id);
        return (Product) query.getSingleResult();
    }

    @Override
    public List<Product> list(Category category) {
        Query query;
        query = entityManager.createQuery("SELECT c FROM Product c WHERE c.category = :category", Product.class);
        query.setParameter("category", category);
        return (List<Product>) query.getResultList();
    }

    @Override
    public List<Product> list() {
        Query query = entityManager.createQuery("SELECT g FROM Product g", Product.class);
        return (List<Product>) query.getResultList();
    }

    @Override
    public List<Product> list(String pattern) {
        Query query = entityManager.createQuery("SELECT c FROM Product c WHERE c.name LIKE :pattern", Product.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Product>) query.getResultList();
    }
}
