package com.app.demo.repositories;

import com.app.demo.dao.CustomerDao;
import com.app.demo.entities.CustomerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional()
public class CustomerRepository implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) {
        CustomerEntity newCustomer = entityManager.merge(customer);
        return newCustomer;
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return entityManager.createQuery("FROM CustomerEntity").getResultList();
    }

    @Override
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        return entityManager.merge(customer);
    }

    @Override
    public CustomerEntity getCustomerById(int id) {
        return entityManager.find(CustomerEntity.class, id);
    }

    @Override
    public void deleteCustomerById(CustomerEntity customer) {
        entityManager.remove(customer);
    }

}
