package com.lambdaschool.foundation.models;

import java.io.Serializable;
import java.util.Objects;

public class ProductId implements Serializable {
    private long user;

    private long product;

    public ProductId() {
    }

    public ProductId(long user, long product) {
        this.user = user;
        this.product = product;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    @Override
   public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductId that = (ProductId) o;
        return this.user == that.user &&
                this.product == that.product;









    }

    @Override
    public int hashCode() {
        return 37;
    }
}