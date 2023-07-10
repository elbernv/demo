package com.app.demo.dao;

import com.app.demo.entities.ProductEntity;

import java.util.List;

public interface ProductDao {
    ProductEntity createProduct(ProductEntity product);

    ProductEntity findProductById(int id);

    List<ProductEntity> getAllProducts();

    ProductEntity updateProduct(ProductEntity product);

    void deleteProduct(ProductEntity product);
}
