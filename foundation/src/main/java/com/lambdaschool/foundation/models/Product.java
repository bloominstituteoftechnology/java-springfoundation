package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    private String product;
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long productid;


@OneToMany(mappedBy="product",cascade = CascadeType.ALL,orphanRemoval = true)
@JsonIgnoreProperties(value = "products")
private Set<UserProduct> users = new HashSet<>();



    public Product() {
    }

    public Product(String product ) {
        this.product = product;

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

    public Set<UserProduct> getUsers() {
        return users;
    }

    public void setUsers(Set<UserProduct> userproducts) {
        this.users = userproducts;
    }
}
