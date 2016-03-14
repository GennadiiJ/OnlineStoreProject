package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.domain.*;
import ua.services.ContactService;
import ua.services.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private SessionService sessionService;


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", contactService.listCategories());

        return "index_categories";
    }


    @RequestMapping("/edit")
    public String editPage(Model model) {
        model.addAttribute("categories", contactService.listCategories());
        model.addAttribute("products", contactService.listProducts());
        model.addAttribute("orders", contactService.listOrders());

        return "edit_page";
    }


    @RequestMapping("/category/{id}")
    public String listCategory(@PathVariable(value = "id") long categoryId, Model model) {
        Category category = contactService.findCategory(categoryId);
        model.addAttribute("categories", contactService.listCategories());
        model.addAttribute("products", contactService.listProducts(category));

        return "index_products";
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("categories", contactService.listCategories());
        model.addAttribute("products", contactService.searchProducts(pattern));

        return "index_products";
    }

    /*PRODUCT ADD-DELETE*/

   @RequestMapping(value = "/edit/product/delete", method = RequestMethod.POST)
    public String searchProd(@RequestParam(value = "product") long toDeleteId, Model model) {
       contactService.deleteProduct(toDeleteId);
       model.addAttribute("categories", contactService.listCategories());
       model.addAttribute("products", contactService.listProducts());

       return "index_categories";
    }


    @RequestMapping(value="/edit/product/add", method = RequestMethod.POST)
    public String productAdd(@RequestParam(value = "category") long categoryId,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam int price,
                             Model model)
    {
        Category category = contactService.findCategory(categoryId);
        Product product = new Product(category, name, description, price);
        contactService.addProduct(product);
        model.addAttribute("categories", contactService.listCategories());

        return "index_categories";
    }

    /*CATEGORY ADD-DELETE*/

    @RequestMapping(value = "/edit/category/delete", method = RequestMethod.POST)
    public String searchCat(@RequestParam(value = "category") long toDeleteId, Model model) {
        contactService.deleteCategory(toDeleteId);
        model.addAttribute("categories", contactService.listCategories());

        return "index_categories";
    }


    @RequestMapping(value="/edit/category/add", method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name,
                           @RequestParam(value = "picture") MultipartFile picture,
                           Model model)
    {
        try {
            Category category = new Category(
                    name,
                    picture.isEmpty() ? null : new Picture(picture.getOriginalFilename(), picture.getBytes())
            );
            contactService.addCategory(category);
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }

        model.addAttribute("categories", contactService.listCategories());

        return "index_categories";
    }

    /*CART*/

    @RequestMapping(value = "/product/buy/{id}", method = RequestMethod.POST)
    public String buyProduct(@PathVariable(value = "id") long productId,
                             Model model,
                             HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        sessionService.checkCartSession(session);

        Product product = contactService.findProduct(productId);
        ArrayList<Product> cart = sessionService.getCart(session);
        cart.add(product);

        //Counting order price
        int price = 0;
        for (Product p : cart) {
            price = price + p.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart", cart);
        model.addAttribute("categories", contactService.listCategories());

        return "cart";
    }


    @RequestMapping("/cart")
    public String showCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        sessionService.checkCartSession(session);
        List<Product> cart = sessionService.getCart(session);

        //Counting order price
        int price = 0;
        for (Product p : cart) {
            price = price + p.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart", cart);
        model.addAttribute("categories", contactService.listCategories());

        return "cart";
    }


    @RequestMapping(value = "/cart/delete/{id}", method = RequestMethod.POST)
    public String cartDelete(@PathVariable(value = "id") long productId,
                             Model model,
                             HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        sessionService.checkCartSession(session);
        List<Product> cart = sessionService.getCart(session);

        //Deleting product by its id from cart
        Iterator<Product> iterator = cart.iterator();
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getId() == productId){
                iterator.remove();
		        break;
            }
        }

        //Counting order price
        int price = 0;
        for (Product p : cart) {
            price = price + p.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart", cart);
        model.addAttribute("categories", contactService.listCategories());

        return "cart";
    }


    @RequestMapping(value="/cart/pay", method = RequestMethod.POST)
    public String cartPay(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String phone,
                          Model model,
                          HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        sessionService.checkCartSession(session);

        if (sessionService.getCart(session).isEmpty()) {
			model.addAttribute("categories", contactService.listCategories());
            model.addAttribute("session_error", "Session error.");
            return "cart";

        } else {
			
            List<Product> cart = sessionService.getCart(session);
			
			//Counting order price
				int price = 0;
				for (Product p : cart) {
					price = price + p.getPrice();
				}
				
            //Checking user's phone number length
            if ((phone.length() > 13) || (phone.length() < 4)) {
				model.addAttribute("error", "Incorrect phone number. Please enter valid phone number.");
				model.addAttribute("price", price);
				model.addAttribute("cart", cart);
				model.addAttribute("categories", contactService.listCategories());
                return "cart";
				
			//Checking user's input length	
            } else if ((email.length() > 70) || (name.length() > 70) || (email.length() < 4) || (name.length() < 2)) {
                model.addAttribute("error", "Incorrect input. Please enter valid information.");
				model.addAttribute("price", price);
				model.addAttribute("cart", cart);
				model.addAttribute("categories", contactService.listCategories());
                return "cart";
				
            } else {
                Client client = null;
                List<Client> clients = contactService.listClients();

                //Checking if the same client already exists in database
                for (Client c : clients) {
                    if (c.getName().equalsIgnoreCase(name) && c.getEmail().equalsIgnoreCase(email) && c.getPhone().equalsIgnoreCase(phone)) {
                        client = c;
                        break;
                    }
                }

                //If client doesn't exists in database, adding a new client and getting the reference to it from database
                if (client == null) {
                    client = new Client(name, email, phone);
                    contactService.addClient(client);

                    clients = contactService.listClients();
                    for (Client c : clients) {
                        if (c.getName().equalsIgnoreCase(name) && c.getEmail().equalsIgnoreCase(email) && c.getPhone().equalsIgnoreCase(phone)) {
                            client = c;
                            break;
                        }
                    }
                }

                //Creating an order by putting to it product from cart, client and current time (1 product = 1 order)
                for (Product c : cart) {

                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String time = sdf.format(date);

                    Order order = new Order(c, client, time);
                    contactService.addOrder(order);
                }
                cart.clear();
                model.addAttribute("categories", contactService.listCategories());

                return "thanks";
            }
        }
    }

    /*PICTURES*/

    @RequestMapping("/picture/{file_id}")
    public void getPicture(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable("file_id") long fileId)
    {
        try {
            byte[] body = contactService.findPicture(fileId);
            response.setContentType("image/png");
            response.getOutputStream().write(body);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*OTHER*/

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "index_categories";
    }


    @RequestMapping("/about")
    public String contact(Model model) {
        model.addAttribute("categories", contactService.listCategories());

        return "about";
    }


    @RequestMapping(value = "/thanks", method = RequestMethod.POST)
    public String thankYou(Model model) {
        model.addAttribute("categories", contactService.listCategories());

        return "thanks";
    }
}
