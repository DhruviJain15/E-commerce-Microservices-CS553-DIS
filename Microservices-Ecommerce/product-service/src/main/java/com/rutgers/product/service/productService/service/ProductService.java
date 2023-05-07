package com.rutgers.product.service.productService.service;

import com.rutgers.product.service.productService.dto.ProductRequest;
import com.rutgers.product.service.productService.dto.ProductResponse;
import com.rutgers.product.service.productService.model.Product;
import com.rutgers.product.service.productService.repository.ProductRepository;
import com.rutgers.product.service.productService.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final WebClient.Builder webClientBuilder;
    public Product createProduct(ProductRequest productRequest){

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .id(UUID.randomUUID().toString())
                .build();
        log.info("Product {} is saved", product.getId());

        Integer quantity=100;
        String skuCodes=productRequest.getName();
        String message = webClientBuilder.build().post()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).queryParam("quantity", quantity).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return productRepository.save(product);

    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public Product getProductById(String productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with ID" + productId + "not found in database!"));
        return product;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
