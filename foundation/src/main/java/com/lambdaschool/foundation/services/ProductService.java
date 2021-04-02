package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Product;

import java.util.List;


public interface ProductService {


  Product save(Product product);

     Product findProductById(long id) ;



    Product delete(long id);

    Product update(
            Product updateProduct,
            long id);

    List<Product> findAllProducts();

}
