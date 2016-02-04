package ua.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dao.CategoryDAO;
import ua.dao.ClientDAO;
import ua.dao.OrderDAO;
import ua.dao.ProductDAO;
import ua.domain.Category;
import ua.domain.Client;
import ua.domain.Order;
import ua.domain.Product;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private OrderDAO orderDAO;


    @Transactional
    public void addClient(Client client){
        clientDAO.add(client);
    }

    @Transactional(readOnly=true)
    public List<Client> listClients(){
        return clientDAO.list();
    }

    @Transactional
    public void addOrder(Order order){
        orderDAO.add(order);
    }

    @Transactional(readOnly=true)
    public List<Order> listOrders() {
        return orderDAO.list();
    }

    @Transactional
    public void addProduct(Product product) {
        productDAO.add(product);
    }

    @Transactional
    public void addCategory(Category category) {
        categoryDAO.add(category);
    }

    @Transactional
    public void deleteProduct(long id) {
        productDAO.delete(id);
    }

    @Transactional
    public void deleteCategory(long id) {
        categoryDAO.delete(id);
    }

    @Transactional(readOnly=true)
    public List<Category> listCategories() {
        return categoryDAO.list();
    }

    @Transactional(readOnly=true)
    public List<Product> listProducts(Category category) {
        return productDAO.list(category);
    }

    @Transactional(readOnly=true)
    public List<Product> listProducts() {
        return productDAO.list();
    }

    @Transactional(readOnly=true)
    public Product findProduct(long id) {
        return productDAO.findOne(id);
    }

    @Transactional(readOnly=true)
    public Category findCategory(long id) {
        return categoryDAO.findOne(id);
    }

    @Transactional(readOnly=true)
    public List<Product> searchProducts(String pattern) {
        return productDAO.list(pattern);
    }

    @Transactional(readOnly=true)
    public byte[] findPicture(long id){
        return categoryDAO.getPictureBody(id);
    }
}
