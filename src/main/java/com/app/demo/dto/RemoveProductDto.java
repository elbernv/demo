package com.app.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RemoveProductDto {
    @NotNull(message = "productId is mandatory")
    private Integer productId;

    @NotNull(message = "shipmentId is mandatory")
    private Integer shipmentId;
}
