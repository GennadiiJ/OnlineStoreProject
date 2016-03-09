package ua.services;

import org.springframework.stereotype.Service;
import ua.domain.Product;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class SessionService {

    public void checkCartSession(HttpSession session){

            if ((ArrayList<Product>) session.getAttribute("cart") == null){
                ArrayList<Product> cart = new ArrayList<Product>();
                session.setAttribute("cart", cart);
            }
    }

    public ArrayList<Product> getCart(HttpSession session){
        return (ArrayList<Product>) session.getAttribute("cart");
    }
}
