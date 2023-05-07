package com.rutgers.product.service.productService.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Resource not found exception");
    }
    public ResourceNotFoundException(String message){
        super(message );
    }
}
