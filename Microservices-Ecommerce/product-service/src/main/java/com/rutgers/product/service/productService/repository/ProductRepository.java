package com.rutgers.product.service.productService.repository;

import com.rutgers.product.service.productService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
