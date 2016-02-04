package ua.dao;

import ua.domain.Category;
import java.util.List;

public interface CategoryDAO {
    void add(Category category);
    void delete(long id);
    Category findOne(long id);
    List<Category> list();
    byte[] getPictureBody(long id);
}
