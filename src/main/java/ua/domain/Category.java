package ua.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Categories")
public class Category {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy="category", cascade=CascadeType.ALL)
    private List<Product> products = new ArrayList<Product>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    public Category() {}

    public Category(String name, Picture picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
