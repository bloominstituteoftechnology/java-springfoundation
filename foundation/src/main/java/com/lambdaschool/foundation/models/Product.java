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
    private String productid;





    public Product() {
    }

    public Product(String product, String productid) {
        this.product = product;
        this.productid = productid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
