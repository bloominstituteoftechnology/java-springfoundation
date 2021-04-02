package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name= "userproduct")
@IdClass(UserProductId.class)
public class UserProduct extends Auditable implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "product", allowSetters = true)
    private User user;


    @Id
    @ManyToOne
    @JoinColumn(name = "productid")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Product product;

    public UserProduct() {
    }

    public UserProduct(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProduct)) {
            return false;
        }
        UserProduct that = (UserProduct) o;
        return ((this.user == null) ? 0 : this.user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
                ((this.product == null) ? 0 : this.product.getProductid()) == ((that.product == null) ? 0 : that.product.getProductid());
    }

    @Override
    public int hashCode() {
        return 37;
    }
}