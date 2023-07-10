package com.app.demo.services;

import com.app.demo.dto.CreateCustomerDto;
import com.app.demo.entities.CustomerEntity;
import com.app.demo.entities.MembershipEntity;
import com.app.demo.repositories.CustomerRepository;
import com.app.demo.repositories.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    public CustomerEntity createCustomer(CreateCustomerDto customer){
        MembershipEntity membershipEntity = this.validateMembership(customer.getMembershipId());
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setName(customer.getName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setDni(customer.getDni());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setMembership(membershipEntity);

        return this.customerRepository.createCustomer(customerEntity);
    }

    private MembershipEntity validateMembership(int id){
        MembershipEntity membership = this.membershipRepository.findMembershipById(id);

        if(membership == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "membership Not Found");
        }

        return membership;
    }

    public List<CustomerEntity> getAllCustomers(){
        return this.customerRepository.getAllCustomers();
    }

    public CustomerEntity updateCustomer(CustomerEntity customer, int customerId){
        this.validateCustomer(customerId);
        customer.setId(customerId);
        return this.customerRepository.updateCustomer(customer);
    }

    public CustomerEntity updateCustomer(CustomerEntity customer){
        return this.customerRepository.updateCustomer(customer);
    }

    public CustomerEntity validateCustomer(int customerId){
        CustomerEntity customer = this.customerRepository.getCustomerById(customerId);

        if(customer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer Not Found");
        }

        return customer;
    }

    public CustomerEntity getCustomerById(int customerId){
        return this.validateCustomer(customerId);
    }

    public HashMap<String, String> deleteCustomer(int customerId){
        CustomerEntity customer = this.validateCustomer(customerId);
        this.customerRepository.deleteCustomerById(customer);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "customer deleted");

        return response;
    }
}
