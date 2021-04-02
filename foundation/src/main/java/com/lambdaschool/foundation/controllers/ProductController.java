package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Product;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products",
            produces = "application/json")
    public ResponseEntity<?> listAllProducts() {
        List<Product> myProducts= productService.findAllProducts();
        return new ResponseEntity<>(myProducts,
                HttpStatus.OK);
    }
    @GetMapping(value = "/product/{productId}",
            produces = "application/json")
    public ResponseEntity<?> getProductById(
            @PathVariable
                    Long productId) {
        Product p = productService.findProductById(productId);
        return new ResponseEntity<>(p,
                HttpStatus.OK);



    }
    @PutMapping(value = "/product/{productid}",
            consumes = "application/json")
    public ResponseEntity<?> updateProduct(
            @Valid
            @RequestBody
                    Product updateProduct,
            @PathVariable
                    long productid)
    {
        updateProduct.setProductid(productid);
        productService.save(updateProduct);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<?> deleteProductById(
            @PathVariable
                    long id)
    {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
