package com.app.demo.controllers;

import com.app.demo.dto.RemoveProductDto;
import com.app.demo.dto.RenewMembershipDto;
import com.app.demo.entities.ShipmentEntity;
import com.app.demo.services.MainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    MainService mainService;

    @DeleteMapping(value = "/ship/removeProduct")
    public ShipmentEntity removeProduct(@Valid @RequestBody RemoveProductDto body){
        return this.mainService.removeProduct(body);
    }

    @PostMapping(value = "/client/renewMembership")
    public HashMap<String, String> renewMembership(@Valid @RequestBody RenewMembershipDto body){
        return this.mainService.renewMembership(body);
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
