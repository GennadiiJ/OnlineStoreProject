package ua.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clients")
public class Client{

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    List<Order> orders = new ArrayList<>();

    private String name;
    private String email;
    private String phone;

    public Client(){}

    public Client(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
