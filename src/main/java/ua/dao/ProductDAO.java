package ua.dao;

import ua.domain.Category;
import ua.domain.Product;
import java.util.List;

public interface ProductDAO {
    void add(Product product);
    void delete(long id);
    Product findOne(long id);
    List<Product> list(Category category);
    List<Product> list();
    List<Product> list(String pattern);
}