package com.app.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewMembershipDto {
    @NotNull(message = "customerId is mandatory")
    private Integer customerId;

    @NotNull(message = "membershipId is mandatory")
    private Integer membershipId;
}
