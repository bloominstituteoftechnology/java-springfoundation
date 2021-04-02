package com.lambdaschool.foundation.models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    private String product;
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long productid;


@ManyToOne
@JoinColumn(name="userid",nullable = false)
private User user;



    public Product() {
    }

    public Product(String product, long productid, User user) {
        this.product = product;
        this.productid = productid;
        this.user = user;
    }



    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
