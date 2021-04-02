package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.models.Role;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


public interface ProductService {


  Product save(Product product);

     Product findProductById(long id) ;



    void delete(long id);

    Product update(
            Product updateProduct,
            long id);

    List<Product> findAllProducts();

}
