package ua.dao;

import org.springframework.stereotype.Repository;
import ua.domain.Order;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Order order) {
        entityManager.merge(order);
    }

    @Override
    public List<Order> list() {
        Query query = entityManager.createQuery("SELECT g FROM Order g", Order.class);
        return (List<Order>) query.getResultList();
    }
}
