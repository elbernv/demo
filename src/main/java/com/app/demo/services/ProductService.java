package com.app.demo.services;

import com.app.demo.entities.ProductEntity;
import com.app.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity createProduct(ProductEntity product){
        return this.productRepository.createProduct(product);
    }

    public List<ProductEntity> getAllProducts(){
        return this.productRepository.getAllProducts();
    }

    public ProductEntity updateProduct(ProductEntity product, int id){
        this.validateProduct(id);
        product.setId(id);
        return this.productRepository.updateProduct(product);
    }

    public ProductEntity validateProduct(int id){
        ProductEntity product = this.productRepository.findProductById(id);

        if(product == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product Not Found");
        }

        return product;
    }

    public ProductEntity getProductById(int id){
        return this.validateProduct(id);
    }

    public HashMap<String, String> deleteProduct(int id){
        try {
            ProductEntity product = this.validateProduct(id);
            this.productRepository.deleteProduct(product);
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "product deleted");

            return response;
        }
        catch (DataIntegrityViolationException err){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "some shipments depend on this product, it cannot be deleted.");
        }
    }
}
