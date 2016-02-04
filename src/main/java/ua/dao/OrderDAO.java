package ua.dao;

import ua.domain.Order;
import java.util.List;

public interface OrderDAO {
    void add(Order order);
    List<Order> list();
}
