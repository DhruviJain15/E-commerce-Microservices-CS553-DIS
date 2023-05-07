package com.rutgers.product.service.productService.controller;

import com.rutgers.product.service.productService.dto.ProductRequest;
import com.rutgers.product.service.productService.dto.ProductResponse;
import com.rutgers.product.service.productService.model.Product;
import com.rutgers.product.service.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest){
        Product product = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> productResponses = productService.getAllProducts();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId){
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
