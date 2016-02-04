package ua.dao;

import org.springframework.stereotype.Repository;
import ua.domain.Client;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Client client) {
        entityManager.merge(client);
    }

    @Override
    public List<Client> list() {
        Query query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
        return (List<Client>) query.getResultList();
    }
}
