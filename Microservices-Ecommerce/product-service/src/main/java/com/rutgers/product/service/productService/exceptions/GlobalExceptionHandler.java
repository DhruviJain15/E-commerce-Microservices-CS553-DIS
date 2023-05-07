package com.rutgers.product.service.productService.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handlerResourceNotFoundException(ResourceNotFoundException exception){
        return exception.getMessage();

    }
}
