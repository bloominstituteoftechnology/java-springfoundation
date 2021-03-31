package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
