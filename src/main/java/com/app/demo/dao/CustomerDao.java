package com.app.demo.dao;
import com.app.demo.entities.CustomerEntity;

import java.util.List;

public interface CustomerDao {
    CustomerEntity createCustomer(CustomerEntity customer);

    List<CustomerEntity> getAllCustomers();

    CustomerEntity updateCustomer(CustomerEntity customer);

    CustomerEntity getCustomerById(int id);

    void deleteCustomerById(CustomerEntity customer);
}
