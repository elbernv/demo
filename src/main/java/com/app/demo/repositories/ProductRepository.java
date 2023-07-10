package com.app.demo.repositories;

import com.app.demo.dao.ProductDao;
import com.app.demo.entities.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional()
public class ProductRepository implements ProductDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return entityManager.merge(product);
    }

    @Override
    public ProductEntity findProductById(int id) {
        return entityManager.find(ProductEntity.class, id);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return entityManager.createQuery("FROM ProductEntity").getResultList();
    }

    @Override
    public ProductEntity updateProduct(ProductEntity product) {
        return this.createProduct(product);
    }

    @Override
    public void deleteProduct(ProductEntity product) {
        entityManager.remove(product);
    }
}
