package com.app.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShipmentDto {
    @NotNull(message = "customerId is mandatory")
    private Integer customerId;

    @NotNull(message = "productsId is mandatory")
    private List<Integer> productsId;
}
