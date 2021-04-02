package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
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
    private UserService userService;
    @Autowired
    private ProductRepository productrepos;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userrepos;

    @Override
    public Product save(Product product) {

            Product newProduct = new Product();

            if (product.getProductid() != 0) {
                productrepos.findById(product.getProductid())
                        .orElseThrow(() -> new ResourceNotFoundException("User id " + product.getProductid() + " not found!"));
                newProduct.setProductid(product.getProductid());
            }
            newProduct.setProduct(product.getProduct());
            newProduct.setProductid(product.getProductid());


            newProduct.getUsers()
                    .clear();
            for (UserProduct ur : product.getUsers()) {
                User user = ur.getUser();
                user = userService.findUserById(user.getUserid());
                newProduct.getUsers()
                        .add(new UserProduct(user,
                                newProduct));
            }


            return productrepos.save(newProduct);
        }
        @Override
        public Product findProductById ( long id){
            return productrepos.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product " + id + " Not Found"));
        }

        @Transactional
        @Override
        public Product delete (long productid){
            if (productrepos.findById(productid).isPresent()) {
                productrepos.deleteById(productid);
                throw new EntityNotFoundException("product" + productid + "not found!");
            } else {
                throw new EntityNotFoundException("product" + productid + "not found!");
            }
        }
        @Transactional
        @Override
        public Product update (Product updateProduct,long productid) {
            Product currentProduct = productrepos.findById(productid)
                    .orElseThrow(() -> new EntityNotFoundException("Product"+ productid + "not found"));


               currentProduct.setProduct(updateProduct.setProduct());


            updateProduct.getUsers()
                    .clear();
            for (UserProduct up : updateProduct.getUsers()) {
                User user = up.getUser();
                user = userService.findUserById(user.getUserid());
                updateProduct.getUsers()
                        .add(new UserProduct(user,
                                updateProduct));
            }
            return productrepos.save(currentProduct);

        }
            @Override
        public List<Product> findAllProducts () {
            List<Product> list = new ArrayList<>();
            productrepos.findAll()
                    .iterator()
                    .forEachRemaining(list::add);
            return list;
        }

    }
