package com.app.demo.controllers;

import com.app.demo.entities.MembershipEntity;
import com.app.demo.services.MembershipService;
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
public class MembershipController {
    @Autowired
    private MembershipService membershipService;

    @PostMapping(value = "/memberships")
    public MembershipEntity createMembership(@Valid @RequestBody MembershipEntity membership){
        return this.membershipService.createMembership(membership);
    }

    @GetMapping(value = "/memberships")
    public List<MembershipEntity> getAllMemberships(){
        return this.membershipService.getAllMemberships();
    }

    @PatchMapping(value = "/memberships/{id}")
    public MembershipEntity updateMembership(@Valid @RequestBody MembershipEntity body, @PathVariable int id){
        return this.membershipService.updateMembership(body, id);
    }

    @GetMapping(value = "/memberships/{id}")
    public MembershipEntity getMembershipById(@PathVariable int id){
        return this.membershipService.getMembershipById(id);
    }

    @DeleteMapping(value = "/memberships/{id}")
    public HashMap<String, String> deleteMembership(@PathVariable int id){
        return this.membershipService.deleteMembership(id);
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
