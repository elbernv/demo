package com.app.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerDto {
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotBlank(message = "dni is mandatory")
    private String dni;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotNull(message = "membershipId is mandatory")
    private Integer membershipId;
}
