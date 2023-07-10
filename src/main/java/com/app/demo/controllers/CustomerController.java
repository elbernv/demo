package com.app.demo.controllers;

import com.app.demo.dto.CreateCustomerDto;
import com.app.demo.entities.CustomerEntity;
import com.app.demo.services.CustomerService;
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
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/customers")
    public List<CustomerEntity> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping(value = "/customers/{customerId}")
    public CustomerEntity getCustomerById(@PathVariable int customerId) {
        return this.customerService.getCustomerById(customerId);
    }

    @PostMapping(value = "/customers")
    public CustomerEntity createCustomer(@Valid @RequestBody CreateCustomerDto body) {
        return this.customerService.createCustomer(body);
    }

    @PatchMapping(value="/customers/{customerId}")
    public CustomerEntity updateCustomer(@Valid @RequestBody CustomerEntity body, @PathVariable int customerId){
        return this.customerService.updateCustomer(body, customerId);
    }

    @DeleteMapping(value = "/customers/{customerId}")
    public HashMap<String, String> deleteCustomer(@PathVariable int customerId){
        return this.customerService.deleteCustomer(customerId);
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
