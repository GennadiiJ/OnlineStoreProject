package ua.dao;

import ua.domain.Client;
import java.util.List;

public interface ClientDAO{
    void add(Client client);
    List<Client> list();
}
