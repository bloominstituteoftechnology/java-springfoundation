package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.repository.ProductRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productrepos;

    @Override
    public Product save(Product product) {
        return productrepos.save(product);
    }

    @Override
    public Product findProductById(long id) {
        return productrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long productid) {
        if (productrepos.findById(productid).isPresent()) {
            productrepos.deleteById(productid);
            throw new EntityNotFoundException("product" + productid + "not found!");
        } else {
            throw new EntityNotFoundException("product" + productid + "not found!");
        }
    }

    @Override
    public Product update(Product updateProduct, long id) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> list = new ArrayList<>();
        productrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

}
