package com.app.demo.controllers;

import com.app.demo.dto.ShipmentDto;
import com.app.demo.entities.ShipmentEntity;
import com.app.demo.services.ShipmentService;
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
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @PostMapping(value = "/shipments")
    public HashMap<String, Object> createShipment(@Valid @RequestBody ShipmentDto shipment){
        return this.shipmentService.createShipment(shipment);
    }

    @GetMapping(value = "/shipments")
    public HashMap<String, Object> getAllShipments(){
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentEntity> shipmentEntities = this.shipmentService.getAllShipments();

        response.put("shipments", shipmentEntities);

        return response;
    }

    @GetMapping(value = "/shipments/{id}")
    public ShipmentEntity getShipmentById(@PathVariable int id){
        return this.shipmentService.getShipmentById(id);
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
