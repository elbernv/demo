package com.app.demo.controllers;

import com.app.demo.entities.ProductEntity;
import com.app.demo.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/products")
    public ProductEntity createProduct(@Valid @RequestBody ProductEntity product){
        return this.productService.createProduct(product);
    }

    @GetMapping(value = "/products")
    public List<ProductEntity> getAllProducts(){
        return this.productService.getAllProducts();
    }

    @PatchMapping(value = "/products/{id}")
    public ProductEntity updateProduct(@Valid @RequestBody ProductEntity body, @PathVariable int id){
        return this.productService.updateProduct(body, id);
    }

    @GetMapping(value = "/products/{id}")
    public ProductEntity getProductById(@PathVariable int id){
        return this.productService.getProductById(id);
    }

    @DeleteMapping(value = "/products/{id}")
    public HashMap<String, String> deleteProduct(@PathVariable int id){
        return this.productService.deleteProduct(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public Map<String, String> handleValidationExceptions(
            ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getReason());
        return error;
    }
}
